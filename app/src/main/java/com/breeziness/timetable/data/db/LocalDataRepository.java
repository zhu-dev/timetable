package com.breeziness.timetable.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.breeziness.timetable.data.bean.CourseBean;
import com.breeziness.timetable.data.bean.CourseNetBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * 基于Rxjava的数据库管理类
 */
public class LocalDataRepository implements LocalDataSource {
    private static volatile LocalDataRepository manager;//懒汉单例模式
    private DataBaseHelper helper;
    private static final String TAG = "LocalDataRepository";

    //私有的构造方法
    private LocalDataRepository(Context context) {
        helper = new DataBaseHelper(context, "timetable.db", null, 2);
    }

    //获取实例的方法
    public static LocalDataRepository getInstance(Context context) {
        //加入double checking
        if (manager != null) {
            return manager;
        }
        synchronized (LocalDataRepository.class) {
            //初始化可能时一个耗时操作，有可能发生指令重排
            //对象没有初始化完成时，子线程就拿到对象，是一个空对象
            //加入volatile关键字，避免指令重排
            if (manager == null) {
                manager = new LocalDataRepository(context);
            }
        }
        return manager;
    }


    @Override
    public Flowable<Boolean> insert(final List<CourseNetBean.DataBean> dataBeans) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                List<CourseBean> courseList = new ArrayList<>();
                for (int i = 0; i < dataBeans.size(); i++) {
                    CourseNetBean.DataBean dataBean = dataBeans.get(i);
                    CourseBean course = new CourseBean();

                    course.setId(dataBean.getId());
                    course.setStartweek(dataBean.getStartweek());
                    course.setEndweek(dataBean.getEndweek());
                    course.setSeq(dataBean.getSeq());
                    course.setCname(dataBean.getCname());
                    course.setCourseno(dataBean.getCourseno());
                    course.setWeek(dataBean.getWeek());

                    if (dataBean.getName() == null) {
                        course.setName("无");
                    } else {
                        course.setName(dataBean.getName().toString());
                    }

                    if (dataBean.getCroomno() == null) {
                        course.setCroomno("无");
                    } else {
                        course.setCroomno(dataBean.getCroomno().toString());
                    }

                    if (dataBean.getTerm() == null) {
                        course.setTerm("无");
                    } else {
                        course.setTerm(dataBean.getTerm().toString());
                    }

                    courseList.add(course);

                    cv.put("cname", course.getCname());
                    cv.put("courseid", course.getCourseno());
                    cv.put("itemid", course.getId());
                    cv.put("teachername", course.getName());
                    cv.put("term", course.getTerm());
                    cv.put("startweek", course.getStartweek());
                    cv.put("endweek", course.getEndweek());
                    cv.put("week", course.getWeek());
                    cv.put("croomno", course.getCroomno());
                    cv.put("seq", dataBean.getSeq());
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

    @Override
    public Flowable<Boolean> update(final List<CourseNetBean.DataBean> dataBeans) {
        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                for (int i = 0; i < dataBeans.size(); i++) {
                    CourseNetBean.DataBean dataBean = dataBeans.get(i);
                    CourseBean course = new CourseBean();
                    course.setId(dataBean.getId());
                    course.setStartweek(dataBean.getStartweek());
                    course.setEndweek(dataBean.getEndweek());
                    course.setSeq(dataBean.getSeq());
                    course.setCname(dataBean.getCname());
                    course.setCourseno(dataBean.getCourseno());
                    course.setWeek(dataBean.getWeek());
                    if (dataBean.getName() == null) {
                        course.setName("无");
                    } else {
                        course.setName(dataBean.getName().toString());
                    }

                    if (dataBean.getCroomno() == null) {
                        course.setCroomno("无");
                    } else {
                        course.setCroomno(dataBean.getCroomno().toString());
                    }

                    if (dataBean.getTerm() == null) {
                        course.setTerm("无");
                    } else {
                        course.setTerm(dataBean.getTerm().toString());
                    }

                    cv.put("cname", course.getCname());
                    cv.put("courseid", course.getCourseno());
                    cv.put("itemid", course.getId());
                    cv.put("teachername", course.getName());
                    cv.put("term", course.getTerm());
                    cv.put("startweek", course.getStartweek());
                    cv.put("endweek", course.getEndweek());
                    cv.put("week", course.getWeek());
                    cv.put("croomno", course.getCroomno());
                    cv.put("seq", dataBean.getSeq());

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

    @Override
    public Flowable<List<CourseBean>> getAll(final String tableName) {
        return Flowable.create(new FlowableOnSubscribe<List<CourseBean>>() {
            @Override
            public void subscribe(FlowableEmitter<List<CourseBean>> emitter) throws Exception {
                List<CourseBean> dataList = new ArrayList<>();
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
                if (cursor.moveToFirst()) {
                    //遍历所有的数据
                    do {
                        CourseBean dataBean = new CourseBean();
                        dataBean.setCname(cursor.getString(cursor.getColumnIndex("cname")));
                        dataBean.setId(cursor.getInt(cursor.getColumnIndex("itemid")));
                        dataBean.setCourseno(cursor.getString(cursor.getColumnIndex("courseid")));
                        dataBean.setName(cursor.getString(cursor.getColumnIndex("teachername")));
                        dataBean.setStartweek(cursor.getInt(cursor.getColumnIndex("startweek")));
                        dataBean.setEndweek(cursor.getInt(cursor.getColumnIndex("endweek")));
                        dataBean.setWeek(cursor.getInt(cursor.getColumnIndex("week")));
                        dataBean.setTerm(cursor.getString(cursor.getColumnIndex("term")));
                        dataBean.setSeq(cursor.getString(cursor.getColumnIndex("seq")));
                        dataBean.setCroomno(cursor.getString(cursor.getColumnIndex("croomno")));
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

    @Override
    public Flowable<List<CourseBean>> getCourseFromDB() {
        return null;
    }


    /**
     * 添加一门课程
     *
     * @param courseid
     * @return
     */
    @Override
    public Flowable<CourseNetBean.DataBean> addCourse(String courseid) {
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

    //测试插入对象
//    @Override
//    public Flowable<Boolean> insertCourses(final List<CourseNetBean.DataBean> dataBeans) {
//        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
//            @Override
//            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
//                SQLiteDatabase db = helper.getWritableDatabase();
//                for (int i = 0; i < dataBeans.size(); i++) {
//                    CourseNetBean.DataBean dataBean = dataBeans.get(i);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                    objectOutputStream.writeObject(dataBean);
//                    objectOutputStream.flush();
//                    byte[] data = byteArrayOutputStream.toByteArray();
//                    objectOutputStream.close();
//                    byteArrayOutputStream.close();
//                    String sql = "insert into course(data) values (?)";
//                    db.execSQL(sql, new Object[]{data});
//                }
//                emitter.onNext(true);
//                emitter.onComplete();//完成事件
//
//            }
//        }, BackpressureStrategy.BUFFER);
//    }


//    @Override
//    public Flowable<Boolean> updateCourses(final List<CourseNetBean.DataBean> dataBeans) {
//        return Flowable.create(new FlowableOnSubscribe<Boolean>() {
//            @Override
//            public void subscribe(FlowableEmitter<Boolean> emitter) throws Exception {
//                SQLiteDatabase db = helper.getWritableDatabase();
//                for (int i = 0; i < dataBeans.size(); i++) {
//                    CourseNetBean.DataBean dataBean = dataBeans.get(i);
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                    objectOutputStream.writeObject(dataBean);
//                    objectOutputStream.flush();
//                    byte[] data = byteArrayOutputStream.toByteArray();
//                    objectOutputStream.close();
//                    byteArrayOutputStream.close();
//                    String sql = "update course set data = (?)";
//                    db.execSQL(sql, new Object[]{data});
//                    //判断是否更新成功，并决定是否发射事件
//                }
//                emitter.onNext(true);
//                emitter.onComplete();//完成事件
//            }
//        }, BackpressureStrategy.BUFFER);
//    }


//    @Override
//    public Flowable<List<CourseNetBean.DataBean>> getAllCourses(final String tableName) {
//        return Flowable.create(new FlowableOnSubscribe<List<CourseNetBean.DataBean>>() {
//            @Override
//            public void subscribe(FlowableEmitter<List<CourseNetBean.DataBean>> emitter) throws Exception {
//                List<CourseNetBean.DataBean> dataList = new ArrayList<>();
//                StringBuilder sql = new StringBuilder();
//                sql.append("SELECT * FROM ").append(tableName);
//                Log.e(TAG, "subscribe: --sql---" + sql);
//                SQLiteDatabase db = helper.getWritableDatabase();
//                Cursor cursor = db.rawQuery(sql.toString(), null);
//                if (cursor != null) {
//                    while (cursor.moveToNext()) {
//                        byte[] data = cursor.getBlob(cursor.getColumnIndex("data"));
//                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
//                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//                        CourseNetBean.DataBean dataBean = (CourseNetBean.DataBean) objectInputStream.readObject();
//                        objectInputStream.close();
//                        byteArrayInputStream.close();
//                        dataList.add(dataBean);
//                    }
//                    cursor.close();
//                }
//                db.close();
//                emitter.onNext(dataList);//发射结果
//                emitter.onComplete();//完成事件
//            }
//        }, BackpressureStrategy.BUFFER);
//    }


}
