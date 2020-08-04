package com.myself.pulgins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.StringUtility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * @author liangsy
 */
public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        if (hasLombok) {
            // 添加domain的import
            topLevelClass.addImportedType("lombok.Data");
            // 添加domain的注解
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addImportedType("lombok.Builder");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addAnnotation("@NoArgsConstructor");
        }

        topLevelClass.addJavaDocLine("/**");

        String remarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" * " + remarkLine);
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(" * @description ").append(introspectedTable.getFullyQualifiedTable());
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @author ").append(System.getProperties().getProperty("user.name"));
        topLevelClass.addJavaDocLine(sb.toString());
        sb.setLength(0);
        sb.append(" * @date ");
        sb.append(getDateString());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
        //自定义字段
        List<IntrospectedColumn> allColumns = introspectedTable.getAllColumns();
        allColumns.forEach(introspectedColumn -> {
            Field field = new Field();
            field.setVisibility(JavaVisibility.PUBLIC);
            field.setStatic(true);
            field.setFinal(true);
            field.setType(new FullyQualifiedJavaType("String"));
            field.setName(humpToUnderline(introspectedColumn.getJavaProperty()));
            field.setInitializationString("\""+introspectedColumn.getJavaProperty()+"\"");
            topLevelClass.addField(field);
        });
        return true;
    }

//    生成注解
//    @Override
//    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
//                                       IntrospectedTable introspectedTable, ModelClassType modelClassType) {
//        field.addJavaDocLine("/**");
//        String remarks = introspectedColumn.getRemarks();
//        if (StringUtility.stringHasValue(remarks)) {
//            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
//            for (String remarkLine : remarkLines) {
//                field.addJavaDocLine(" * " + remarkLine);
//            }
//        }
//        field.addJavaDocLine(" */");
//        return true;
//    }

//    @Override
//    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        // 添加Mapper的import
//        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
//
//        // 添加Mapper的注解
//        interfaze.addAnnotation("@Mapper");
//        return true;
//    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成getter
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        return !hasLombok;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成setter
        boolean hasLombok = Boolean.parseBoolean(getProperties().getProperty("hasLombok", "false"));
        return !hasLombok;
    }

    protected String getDateString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(LocalDateTime.now());
    }

    /**
     * 功能：驼峰命名转下划线命名
     * 小写和大写紧挨一起的地方,加上分隔符,然后全部转大写
     */
    public static String humpToUnderline(String column)
    {
        String separator = "_";
        column = column.replaceAll("([a-z])([A-Z])", "$1"+separator+"$2").toUpperCase();
        return column;
    }
}
