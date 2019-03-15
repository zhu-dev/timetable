package com.breeziness.timetable.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.breeziness.timetable.data.bean.CourseBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * 基于Rxjava的数据库管理类
 */
public class DataBaseManager implements DataBaseService {
    private static volatile DataBaseManager manager;//懒汉单例模式
    private DataBaseHelper helper;


    //私有的构造方法
    private DataBaseManager(Context context) {
        helper = new DataBaseHelper(context, "timetable.db", null, 2);
    }

    //获取实例的方法
    public static DataBaseManager getInstance(Context context) {
        //加入double checking
        if (manager != null) {
            return manager;
        }
        synchronized (DataBaseManager.class) {
            //初始化可能时一个耗时操作，有可能发生指令重排
            //对象没有初始化完成时，子线程就拿到对象，是一个空对象
            //加入volatile关键字，避免指令重排
            if (manager == null) {
                manager = new DataBaseManager(context);
            }
        }
        return manager;
    }

    /**
     * 插入课程
     *
     * @return
     */
    @Override
    public Flowable<Boolean> insertCourse(final List<CourseBean.DataBean> dataBeans) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                for (int i = 0; i < dataBeans.size(); i++) {
                    CourseBean.DataBean dataBean = dataBeans.get(i);
                    cv.put("cname", dataBean.getCname());
                    cv.put("courseid", dataBean.getCourseno());
                    cv.put("itemid", dataBean.getId());
                    cv.put("teachername", dataBean.getName());
                    cv.put("term", dataBean.getTerm());
                    cv.put("startweek", dataBean.getStartweek());
                    cv.put("endweek", dataBean.getEndweek());
                    cv.put("week", dataBean.getWeek());

                    //判断是否插入成功，并决定是否发射事件,这里的处理还需优化  有点问题
//                    if (db.insert("course", null, cv) != -1) {
//                        emitter.onNext(true);
//                    } else {
//                        emitter.onNext(false);
//                    }
                    db.insert("course", null, cv);
                }
                emitter.onNext(true);
                emitter.onComplete();//完成事件
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 更新课程
     *
     * @return
     */
    @Override
    public Flowable<Boolean> updataCourse(final List<CourseBean.DataBean> dataBeans) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                for (int i = 0; i < dataBeans.size(); i++) {
                    CourseBean.DataBean dataBean = dataBeans.get(i);
                    cv.put("cname", dataBean.getCname());
                    cv.put("courseid", dataBean.getCourseno());
                    cv.put("itemid", dataBean.getId());
                    cv.put("teachername", dataBean.getName());
                    cv.put("term", dataBean.getTerm());
                    cv.put("startweek", dataBean.getStartweek());
                    cv.put("endweek", dataBean.getEndweek());
                    cv.put("week", dataBean.getWeek());

                    String args = i + "";//限制条件
                    //判断是否更新成功，并决定是否发射事件
                    if (db.update("course", cv, "id = ?", new String[]{args}) > -1) {
                        emitter.onNext(true);
                    } else {
                        emitter.onNext(false);
                    }
                }
                emitter.onComplete();//完成事件
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 查询全部课程
     *
     * @return
     */
    @Override
    public Flowable<List<CourseBean.DataBean>> getAllCourse(final String tableName) {
        return Flowable.create(new FlowableOnSubscribe<List<CourseBean.DataBean>>() {
            @Override
            public void subscribe(FlowableEmitter<List<CourseBean.DataBean>> emitter) throws Exception {
                List<CourseBean.DataBean> dataList = new ArrayList<>();
                StringBuilder sql = new StringBuilder();
                sql.append("select * from ").append(tableName);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery(sql.toString(), null);
                if (cursor.moveToFirst()) {
                    //遍历所有的数据
                    do {
                        CourseBean.DataBean dataBean = new CourseBean.DataBean();
                        dataBean.setCname(cursor.getString(cursor.getColumnIndex("cname")));
                        dataBean.setId(cursor.getInt(cursor.getColumnIndex("itemid")));
                        dataBean.setCourseno(cursor.getString(cursor.getColumnIndex("courseid")));
                        dataBean.setName(cursor.getString(cursor.getColumnIndex("teachername")));
                        dataBean.setStartweek(cursor.getInt(cursor.getColumnIndex("startweek")));
                        dataBean.setEndweek(cursor.getInt(cursor.getColumnIndex("endweek")));
                        dataBean.setWeek(cursor.getInt(cursor.getColumnIndex("week")));
                        dataList.add(dataBean);
                    } while (cursor.moveToNext());
                }
                cursor.close();//关闭指针
                db.close();//关闭数据库

                emitter.onNext(dataList);//发射结果
                emitter.onComplete();//完成事件
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 添加一门课程
     *
     * @param courseid
     * @return
     */
    @Override
    public Flowable<CourseBean.DataBean> addCourse(String courseid) {
        return null;
    }

    /**
     * 移除一门课程
     *
     * @param courseid
     * @return
     */
    @Override
    public Flowable<Boolean> removeCourse(String courseid) {
        return null;
    }
}
