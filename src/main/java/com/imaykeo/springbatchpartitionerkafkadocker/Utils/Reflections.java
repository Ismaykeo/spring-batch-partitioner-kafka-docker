package com.imaykeo.springbatchpartitionerkafkadocker.Utils;

import lombok.SneakyThrows;
import org.springframework.batch.item.file.transform.FieldSet;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.util.StringUtils.uncapitalize;

public class Reflections {

    private Reflections() {
    }

    private static Field[] getFields(Class<?> targeClass, Class<?> annotationClass) {
        if (annotationClass == null) {
            throw new IllegalArgumentException("annotationClass must be specified");
        }

        return Arrays.stream(targeClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent((Class) annotationClass) || field.getDeclaredAnnotations().length == 0).map(field -> {
                    return field;
                }).collect(Collectors.toList()).toArray(Field[]::new);
    }

    public static String[] getFieldsNames(Class<?> targeClass, Class<?> annotationClass) {
        return Arrays.stream(getFields(targeClass, annotationClass)).map(field -> {
            return field.getName();
        }).collect(Collectors.toList()).toArray(String[]::new);
    }

    public static String getInsertClass(Class<?> targeClass, Class<?> annotationClass) {
        if (annotationClass == null) {
            throw new IllegalArgumentException("annotationClass must be specified");
        }
        String[] fields = getFieldsNames(targeClass, annotationClass);

        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ");
        sql.append(targeClass.getSimpleName().toLowerCase());
        sql.append(" ( ");
        sql.append(Arrays.stream(fields).map(field -> field.toLowerCase()).collect(Collectors.joining(",", "", "")));
        sql.append(" ) VALUES ");
        StringJoiner stringJoiner = new StringJoiner(" ?, ", " ( ", " ? );");

        IntStream.range(0, fields.length).forEach(index -> {
            stringJoiner.add("");
        });
        sql.append(stringJoiner);

        return sql.toString();
    }

    public static void getFillPreparedStament(Object targeClass, Class<?> annotationClass, PreparedStatement ps) {
        Field[] fields = getFields(targeClass.getClass(), annotationClass);
        Arrays.stream(fields).sorted(Comparator.comparing(Field::getName));
        IntStream.range(1, fields.length + 1).forEach(index -> {
            Field field = fields[index - 1];

            try {
                field.setAccessible(true);
                if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    ps.setBoolean(index, field.getBoolean(targeClass));
                } else if (field.getType() == String.class) {
                    ps.setString(index, (String) field.get(targeClass));
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    ps.setInt(index, (Integer) field.get(targeClass));
                } else if (field.getType() == long.class || field.getType() == Long.class) {
                    ps.setLong(index, (Long) field.get(targeClass));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    ps.setDouble(index, (double) field.get(targeClass));
                } else if (field.getType() == LocalDate.class) {
                    ps.setDate(index, (java.sql.Date) field.get(targeClass));
                } else if (field.getType() == LocalTime.class) {
                    ps.setTime(index, (Time) field.get(targeClass));
                } else if (field.getType() == java.sql.Date.class) {
                    ps.setDate(index, (java.sql.Date) field.get(targeClass));
                } else if (field.getType() == Date.class) {
                    ps.setDate(index, new java.sql.Date(((java.util.Date) field.get(targeClass)).getTime()));
                } else if (field.getType() == Time.class) {
                    ps.setTime(index, (Time) field.get(targeClass));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println(field.getName());
                ex.printStackTrace();
            }
        });

    }

    public static <E> E extractFieldSet(FieldSet fieldSet, Class<?> targeClass, Class<?> annotationClass) throws Exception {
        Field[] fields = getFields(targeClass, annotationClass);
        Constructor<?> constructor = targeClass.getConstructor();
        constructor.setAccessible(true);
        E clonado = (E) constructor.newInstance();
        Class<?> clazz = clonado.getClass();
        IntStream.range(0, fields.length).forEach(index -> {
            Field field = fields[index];
            try {
                field.setAccessible(true);
                if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    field.setBoolean(clonado, fieldSet.readBoolean(field.getName()));
                } else if (field.getType() == String.class) {
                    field.set(clonado, fieldSet.readString(field.getName()));
                } else if (field.getType() == int.class || field.getType() == Integer.class) {
                    field.set(clonado, fieldSet.readInt(field.getName()));
                } else if (field.getType() == long.class || field.getType() == Long.class) {
                    field.set(clonado, fieldSet.readLong(field.getName()));
                } else if (field.getType() == double.class || field.getType() == Double.class) {
                    field.set(clonado, fieldSet.readDouble(field.getName()));
                } else if (field.getType() == LocalDate.class) {
                    field.set(clonado, fieldSet.readDate(field.getName(), "MM/dd/yyyy"));
                } else if (field.getType() == LocalTime.class) {
                    field.set(clonado, fieldSet.readDate(field.getName(), "HH:mm:ss"));
                } else if (field.getType() == Date.class || field.getType() == java.sql.Date.class) {
                    field.set(clonado, fieldSet.readDate(field.getName(), "MM/dd/yyyy"));
                } else if (field.getType() == Time.class) {
                    field.set(clonado, new Time(fieldSet.readDate(field.getName(), "HH:mm:ss").getTime()));
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println(field.getName());
            }
        });
        return clonado;
    }

    @SneakyThrows
    private static boolean isResulSetNoNull(ResultSet resultSet, String columnName) throws SQLException {
        return resultSet.getObject(columnName) != null;
    }

    private static String getNameAtributo(String nameAtributo) {
        return getNameAtributo(nameAtributo, true);
    }

    private static String getNameAtributo(String nameAtributo, boolean basicForm) {
        String name = "";
        if (nameAtributo.startsWith("is")) {
            name = nameAtributo.replaceFirst("is", "");
        } else if (nameAtributo.startsWith("get")) {
            name = nameAtributo.replaceFirst("get", "");
        }
        if (basicForm) {
            return name;
        }
        return replaceLetterUnderscore(uncapitalize(name));
    }

    public static String replaceLetterUnderscore(Object o) {
        return o.toString().replaceAll("([A-Z])", "_$1").toLowerCase();
    }
}
