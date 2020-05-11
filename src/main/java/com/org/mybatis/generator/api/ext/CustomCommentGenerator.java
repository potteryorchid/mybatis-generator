package com.org.mybatis.generator.api.ext;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

public class CustomCommentGenerator implements CommentGenerator {

  private Properties properties = new Properties();
  private boolean suppressDate = false;
  private boolean suppressAllComments = false;
  private boolean addRemarkComments = false;
  private SimpleDateFormat dateFormat;

  public CustomCommentGenerator() {
  }

  @Override
  public void addConfigurationProperties(Properties properties) {
    this.properties.putAll(properties);
    this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));
    this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
    this.addRemarkComments = StringUtility.isTrue(properties.getProperty("addRemarkComments"));
    String dateFormatString = properties.getProperty("dateFormat");
    if (StringUtility.stringHasValue(dateFormatString)) {
      this.dateFormat = new SimpleDateFormat(dateFormatString);
    }
  }

  @Override
  public void addFieldComment(Field field, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (!this.suppressAllComments) {
      field.addJavaDocLine("/**");
      String remarks = introspectedColumn.getRemarks();
      if (this.addRemarkComments && StringUtility.stringHasValue(remarks)) {
        field.addJavaDocLine(" * Database Column Remarks:");
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        String[] var6 = remarkLines;
        int var7 = remarkLines.length;

        for (int var8 = 0; var8 < var7; ++var8) {
          String remarkLine = var6[var8];
          field.addJavaDocLine(" *   " + remarkLine);
        }
      }

      field.addJavaDocLine(" *");
      StringBuilder sb = new StringBuilder();
      sb.append(" * This field corresponds to the database column ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      sb.append('.');
      sb.append(introspectedColumn.getActualColumnName());
      field.addJavaDocLine(sb.toString());
      this.addJavadocTag(field, false);
      field.addJavaDocLine(" */");
    }
  }

  @Override
  public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      field.addJavaDocLine("/**");
      sb.append(" * This field corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      field.addJavaDocLine(sb.toString());
      this.addJavadocTag(field, false);
      field.addJavaDocLine(" */");
    }
  }

  @Override
  public void addModelClassComment(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    if (!this.suppressAllComments && this.addRemarkComments) {
      topLevelClass.addJavaDocLine("/**");
      String remarks = introspectedTable.getRemarks();
      if (this.addRemarkComments && StringUtility.stringHasValue(remarks)) {
        topLevelClass.addJavaDocLine(" * Database Table Remarks:");
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        String[] var5 = remarkLines;
        int var6 = remarkLines.length;

        for (int var7 = 0; var7 < var6; ++var7) {
          String remarkLine = var5[var7];
          topLevelClass.addJavaDocLine(" *   " + remarkLine);
        }
      }

      topLevelClass.addJavaDocLine(" *");
      StringBuilder sb = new StringBuilder();
      sb.append(" * This class corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      topLevelClass.addJavaDocLine(sb.toString());
      topLevelClass.addJavaDocLine(" */");
    }
  }

  @Override
  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      innerClass.addJavaDocLine("/**");
      sb.append(" * This class corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      innerClass.addJavaDocLine(sb.toString());
      this.addJavadocTag(innerClass, false);
      innerClass.addJavaDocLine(" */");
    }
  }

  @Override
  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable,
      boolean b) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      innerClass.addJavaDocLine("/**");
      sb.append(" * This class corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      innerClass.addJavaDocLine(sb.toString());
      this.addJavadocTag(innerClass, b);
      innerClass.addJavaDocLine(" */");
    }
  }

  @Override
  public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      innerEnum.addJavaDocLine("/**");
      sb.append(" * This enum corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      innerEnum.addJavaDocLine(sb.toString());
      this.addJavadocTag(innerEnum, false);
      innerEnum.addJavaDocLine(" */");
    }
  }

  @Override
  public void addGetterComment(Method method, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      method.addJavaDocLine("/**");
      sb.append(" * This method returns the value of the database column ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      sb.append('.');
      sb.append(introspectedColumn.getActualColumnName());
      method.addJavaDocLine(sb.toString());
      method.addJavaDocLine(" *");
      sb.setLength(0);
      sb.append(" * @return the value of ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      sb.append('.');
      sb.append(introspectedColumn.getActualColumnName());
      method.addJavaDocLine(sb.toString());
      this.addJavadocTag(method, false);
      method.addJavaDocLine(" */");
    }
  }

  @Override
  public void addSetterComment(Method method, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      method.addJavaDocLine("/**");
      sb.append(" * This method sets the value of the database column ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      sb.append('.');
      sb.append(introspectedColumn.getActualColumnName());
      method.addJavaDocLine(sb.toString());
      method.addJavaDocLine(" *");
      Parameter parm = (Parameter) method.getParameters().get(0);
      sb.setLength(0);
      sb.append(" * @param ");
      sb.append(parm.getName());
      sb.append(" the value for ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      sb.append('.');
      sb.append(introspectedColumn.getActualColumnName());
      method.addJavaDocLine(sb.toString());
      this.addJavadocTag(method, false);
      method.addJavaDocLine(" */");
    }
  }

  @Override
  public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    if (!this.suppressAllComments) {
      StringBuilder sb = new StringBuilder();
      method.addJavaDocLine("/**");
      sb.append(" * This method corresponds to the database table ");
      sb.append(introspectedTable.getFullyQualifiedTable());
      method.addJavaDocLine(sb.toString());
      this.addJavadocTag(method, false);
      method.addJavaDocLine(" */");
    }
  }

  @Override
  public void addJavaFileComment(CompilationUnit compilationUnit) {

  }

  @Override
  public void addComment(XmlElement xmlElement) {

  }

  @Override
  public void addRootComment(XmlElement xmlElement) {

  }

  @Override
  public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
      Set<FullyQualifiedJavaType> set) {
    set.add(new FullyQualifiedJavaType("javax.annotation.Generated"));
    String comment = "Source Table: " + introspectedTable.getFullyQualifiedTable().toString();
    method.addAnnotation(this.getGeneratedAnnotation(comment));
  }

  @Override
  public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
    set.add(new FullyQualifiedJavaType("javax.annotation.Generated"));
    String comment = "Source field: " + introspectedTable.getFullyQualifiedTable().toString() + "."
        + introspectedColumn.getActualColumnName();
    method.addAnnotation(this.getGeneratedAnnotation(comment));
  }

  @Override
  public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
      Set<FullyQualifiedJavaType> set) {
    set.add(new FullyQualifiedJavaType("javax.annotation.Generated"));
    String comment = "Source Table: " + introspectedTable.getFullyQualifiedTable().toString();
    field.addAnnotation(this.getGeneratedAnnotation(comment));
  }

  @Override
  public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
    set.add(new FullyQualifiedJavaType("javax.annotation.Generated"));
    String comment = "Source field: " + introspectedTable.getFullyQualifiedTable().toString() + "."
        + introspectedColumn.getActualColumnName();
    field.addAnnotation(this.getGeneratedAnnotation(comment));
    if (!this.suppressAllComments && this.addRemarkComments) {
      String remarks = introspectedColumn.getRemarks();
      if (this.addRemarkComments && StringUtility.stringHasValue(remarks)) {
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" * Database Column Remarks:");
        String[] remarkLines = remarks.split(System.getProperty("line.separator"));
        String[] var8 = remarkLines;
        int var9 = remarkLines.length;

        for (int var10 = 0; var10 < var9; ++var10) {
          String remarkLine = var8[var10];
          field.addJavaDocLine(" *   " + remarkLine);
        }

        field.addJavaDocLine(" */");
      }
    }
  }

  @Override
  public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable,
      Set<FullyQualifiedJavaType> set) {
    set.add(new FullyQualifiedJavaType("javax.annotation.Generated"));
    String comment = "Source Table: " + introspectedTable.getFullyQualifiedTable().toString();
    innerClass.addAnnotation(this.getGeneratedAnnotation(comment));
  }

  protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
    javaElement.addJavaDocLine(" *");
    StringBuilder sb = new StringBuilder();
    sb.append(" * Auto generated");
    if (markAsDoNotDelete) {
      sb.append(" do_not_delete_during_merge");
    }

    String s = this.getDateString();
    if (s != null) {
      sb.append(' ');
      sb.append(s);
    }

    javaElement.addJavaDocLine(sb.toString());
  }

  protected String getDateString() {
    if (this.suppressDate) {
      return null;
    } else {
      return this.dateFormat != null ? this.dateFormat.format(new Date()) : (new Date()).toString();
    }
  }

  private String getGeneratedAnnotation(String comment) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("@Generated(");
    if (this.suppressAllComments) {
      buffer.append('"');
    } else {
      buffer.append("value=\"");
    }

    buffer.append(MyBatisGenerator.class.getName());
    buffer.append('"');
    if (!this.suppressDate && !this.suppressAllComments) {
      buffer.append(", date=\"");
      buffer.append(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(ZonedDateTime.now()));
      buffer.append('"');
    }

    if (!this.suppressAllComments) {
      buffer.append(", comments=\"");
      buffer.append(comment);
      buffer.append('"');
    }

    buffer.append(')');
    return buffer.toString();
  }
}
