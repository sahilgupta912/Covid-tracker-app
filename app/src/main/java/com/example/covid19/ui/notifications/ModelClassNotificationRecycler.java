package com.example.covid19.ui.notifications;

public class ModelClassNotificationRecycler {
    private final String txtNews;
    private final String txtDate;

    public ModelClassNotificationRecycler(String txtNews, String txtDate){
        this.txtNews=txtNews;
        this.txtDate=txtDate;
    }

    public String getTxtNews() {
        return txtNews;
    }

    public String getTxtDate() {
        return txtDate;
    }
}
