package com.chukapoka.server.treemember.domain;



public class Memo {
    public int id;
    public String content;
    public boolean isDone;

    public Memo(int id, String content, boolean isDone) {
        this.id = id;
        this.content = content;
        this.isDone = isDone;
    }
}
