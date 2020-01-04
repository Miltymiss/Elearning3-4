package com.other.demo.application.dao;


public final class DbConst {

    public static final String DB_NAME = "note.db"; // 数据库文件名
    public static final int DB_VERSION = 1; // 数据库版本号


    public static class NoteTable {
        public static final String TABLE_NAME = "NoteTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_USER_ID= "userId";
        public static final String COLUMN_USER_NAME = "userName";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_UPLOAD_TIME = "uploadTime";

    }

    public static class CommentTable {
        public static final String TABLE_NAME = "CommentTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT_ID="contentId";
        public static final String COLUMN_COMMENT="comment";
        public static final String COLUMN_COMMENT_NAME = "commentName";
        public static final String COLUMN_COMMENT_TIME = "commentTime";
        public static final String COLUMN_LIKE = "like";
        public static final String COLUMN_COMMENT_COLLECT = "collect";
    }


    public static class CollectTable {
        public static final String TABLE_NAME = "CollectTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT_ID="contentId";
        public static final String COLUMN_USER_ID= "userId";
    }

    public static class LikeTable {
        public static final String TABLE_NAME = "LikeTable";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CONTENT_ID="contentId";
        public static final String COLUMN_USER_ID= "userId";
    }

}
