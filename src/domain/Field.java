package domain;

/**
 * Created by Greg.Chen on 2015/3/17.
 */
public class Field {

    public Field(String _colName, String colName, String _colType,String colType, Boolean autoIncrement, Integer nullable) {
        this._colName = _colName;
        this.colName = colName;
        this._colType = _colType;
        this.colType = colType;
        this.autoIncrement = autoIncrement;
        this.nullable = nullable;
    }

    private String colName;
    private String colType;
    private Boolean autoIncrement;
    private Integer nullable;
    private String _colName;
    private String _colType;

    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    private String colNameForInsert;

    public String getColNameForInsert() {
        return "#{" + colName + "}";
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public Integer getNullable() {
        return nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public String get_colName() {
        return _colName;
    }

    public void set_colName(String _colName) {
        this._colName = _colName;
    }

    public String get_colType() {
        return _colType;
    }

    public void set_colType(String _colType) {
        this._colType = _colType;
    }

    @Override
    public String toString() {
        return "Field{" +
                "colName='" + colName + '\'' +
                ", colType='" + colType + '\'' +
                '}';
    }
}
