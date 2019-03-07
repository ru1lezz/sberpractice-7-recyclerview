package serzhan.com.recyclerview1.recyclerview;

public enum ItemTypes {
    FIRST_VIEW_HOLDER(0),
    SECOND_VIEW_HOLDER(1),
    THIRD_VIEW_HOLDER(2),
    FOURTH_VIEW_HOLDER(3);

    int type;

    ItemTypes(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
