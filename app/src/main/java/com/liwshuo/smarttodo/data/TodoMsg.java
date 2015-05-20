package com.liwshuo.smarttodo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Todo的类
 * Created by shuo on 2015/4/16.
 */
public class TodoMsg implements Parcelable {
    private int _id; //id
    private String todoTitle; //todo的title,即显示在主界面里的内容
    private String todoNote; //todo的备注
    private String todoDate; //todo提醒的日期
    private String todoTime; //todo提醒的时间
    private String todoRepeatWeek; //todo按星期重复
    private String todoRepeatMonth; // Todo按月份重复
    private String todoColor; //Todo的颜色
    private int todoType; //Todo的类型，包括today later done
    private String todoCreateTime; //todo创建的时间
    private String todoUpdateTime; //todo最后一次更新的时间
    private String todoTag; //todo的标签
    private String todoCreateDate;
    private String todoUpdateDate;

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setTodoColor(String todoColor) {
        this.todoColor = todoColor;
    }

    public void setTodoNote(String todoNote) {
        this.todoNote = todoNote;
    }

    public void setTodoCreateTime(String todoCreateTime) {
        this.todoCreateTime = todoCreateTime;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public void setTodoRepeatMonth(String todoRepeatMonth) {
        this.todoRepeatMonth = todoRepeatMonth;
    }

    public void setTodoRepeatWeek(String todoRepeatWeek) {
        this.todoRepeatWeek = todoRepeatWeek;
    }

    public void setTodoTime(String todoTime) {
        this.todoTime = todoTime;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public void setTodoType(int todoType) {
        this.todoType = todoType;
    }

    public void setTodoUpdateTime(String todoUpdateTime) {
        this.todoUpdateTime = todoUpdateTime;
    }

    public void setTodoCreateDate(String todoCreateDate) {
        this.todoCreateDate = todoCreateDate;
    }

    public void setTodoUpdateDate(String todoUpdateDate) {
        this.todoUpdateDate = todoUpdateDate;
    }

    public int get_id() {
        return _id;
    }

    public int getTodoType() {
        return todoType;
    }

    public String getTodoColor() {
        return todoColor;
    }

    public String getTodoNote() {
        return todoNote;
    }

    public String getTodoCreateTime() {
        return todoCreateTime;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public String getTodoRepeatMonth() {
        return todoRepeatMonth;
    }

    public String getTodoRepeatWeek() {
        return todoRepeatWeek;
    }

    public String getTodoTime() {
        return todoTime;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public String getTodoUpdateTime() {
        return todoUpdateTime;
    }

    public void setTodoTag(String todoTag) {
        this.todoTag = todoTag;
    }

    public String getTodoTag() {
        return todoTag;
    }

    public String getTodoCreateDate() {
        return todoCreateDate;
    }

    public String getTodoUpdateDate() {
        return todoUpdateDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(todoTitle);
        dest.writeString(todoNote);
        dest.writeString(todoDate);
        dest.writeString(todoTime);
        dest.writeString(todoRepeatWeek);
        dest.writeString(todoRepeatMonth);
        dest.writeString(todoColor);
        dest.writeInt(todoType);
        dest.writeString(todoCreateTime);
        dest.writeString(todoUpdateTime);
        dest.writeString(todoTag);
        dest.writeString(todoCreateDate);
        dest.writeString(todoUpdateDate);
    }

    public static final Creator<TodoMsg> CREATOR = new Creator<TodoMsg>() {

        @Override
        public TodoMsg createFromParcel(Parcel source) {
            TodoMsg todoMsg = new TodoMsg();
            todoMsg.set_id(source.readInt());
            todoMsg.setTodoTitle(source.readString());
            todoMsg.setTodoNote(source.readString());
            todoMsg.setTodoDate(source.readString());
            todoMsg.setTodoTime(source.readString());
            todoMsg.setTodoRepeatWeek(source.readString());
            todoMsg.setTodoRepeatMonth(source.readString());
            todoMsg.setTodoColor(source.readString());
            todoMsg.setTodoType(source.readInt());
            todoMsg.setTodoCreateTime(source.readString());
            todoMsg.setTodoUpdateTime(source.readString());
            todoMsg.setTodoTag(source.readString());
            todoMsg.setTodoCreateDate(source.readString());
            todoMsg.setTodoUpdateDate(source.readString());
            return todoMsg;
        }

        @Override
        public TodoMsg[] newArray(int size) {
            return new TodoMsg[size];
        }
    };
}
