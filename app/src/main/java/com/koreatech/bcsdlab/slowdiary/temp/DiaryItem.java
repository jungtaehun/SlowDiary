package com.koreatech.bcsdlab.slowdiary.temp;

import java.util.Date;

public class DiaryItem {
    Date writeDate;
    Date openDate;
    String Title;
    String Content;

    public DiaryItem(String t, String c) {
        writeDate = new Date();
        openDate = new Date(2017, 12, 10);
        Title = t;
        Content = c;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}