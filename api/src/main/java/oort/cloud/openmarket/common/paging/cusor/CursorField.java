package oort.cloud.openmarket.common.paging.cusor;

public interface CursorField {
    String getFieldName();
    Class<? extends Comparable<?>> getType();
}
