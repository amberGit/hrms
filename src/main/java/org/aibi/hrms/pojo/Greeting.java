package org.aibi.hrms.pojo;

/**
 * Created by John on 2015/12/13.
 */
public class Greeting {
    private final long id;
    private final  String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
