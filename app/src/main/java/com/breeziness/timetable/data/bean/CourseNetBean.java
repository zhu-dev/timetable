package com.breeziness.timetable.data.bean;

import java.io.Serializable;
import java.util.List;

public class CourseNetBean {

    /**
     * success : true
     * total : 0
     * data : [{"id":508342,"ctype":"RZ","tname":"专业任选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"RFID技术与应用","courseno":"1821925","teacherno":"020409","name":"李天松","term":"2018-2019_2","courseid":"RZ0200145X0","croomno":"17412*","comm":null,"startweek":10,"endweek":16,"oddweek":null,"week":1,"seq":"3","maxcnt":150,"xf":2,"llxs":28,"syxs":4,"sjxs":0,"qtxs":0,"sctcnt":150,"hours":0},{"id":508343,"ctype":"RZ","tname":"专业任选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"RFID技术与应用","courseno":"1821925","teacherno":"020409","name":"李天松","term":"2018-2019_2","courseid":"RZ0200145X0","croomno":"17411*","comm":null,"startweek":10,"endweek":16,"oddweek":null,"week":3,"seq":"4","maxcnt":150,"xf":2,"llxs":28,"syxs":4,"sjxs":0,"qtxs":0,"sctcnt":150,"hours":0},{"id":508334,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"科技文献阅读与写作（信息类）","courseno":"1821921","teacherno":"280097","name":"王娇","term":"2018-2019_2","courseid":"BT0200144X0","croomno":"02207Y","comm":null,"startweek":1,"endweek":8,"oddweek":null,"week":4,"seq":"1","maxcnt":90,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":75,"hours":0},{"id":508335,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"科技文献阅读与写作（信息类）","courseno":"1821921","teacherno":"280097","name":"王娇","term":"2018-2019_2","courseid":"BT0200144X0","croomno":"02207Y","comm":null,"startweek":1,"endweek":8,"oddweek":null,"week":2,"seq":"2","maxcnt":90,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":75,"hours":0},{"id":510604,"ctype":"TW","tname":"文化与艺术","examt":"考查","dptname":null,"dptno":"00","spname":null,"spno":"000000","grade":"2018","cname":"人人爱设计（网络）","courseno":"1822163","teacherno":"1150122","name":"林子铃","term":"2018-2019_2","courseid":"TW0000377X0","croomno":null,"comm":null,"startweek":5,"endweek":5,"oddweek":null,"week":7,"seq":"2","maxcnt":1000,"xf":1,"llxs":16,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":730,"hours":0},{"id":506341,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":19,"oddweek":null,"week":5,"seq":"3","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506342,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":19,"oddweek":null,"week":5,"seq":"4","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506343,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":19,"oddweek":null,"week":5,"seq":"5","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506344,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":6,"seq":"1","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506345,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":7,"seq":"1","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506346,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":6,"seq":"2","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506347,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":7,"seq":"2","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506348,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":6,"seq":"3","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506349,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":7,"seq":"3","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506350,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":6,"seq":"4","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":506351,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"15","spname":null,"spno":"080604","grade":"2016","cname":"电子工程训练2","courseno":"1821018","teacherno":"000932","name":"叶懋","term":"2018-2019_2","courseid":"BS0000011X0","croomno":null,"comm":null,"startweek":16,"endweek":18,"oddweek":null,"week":7,"seq":"4","maxcnt":80,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":80,"hours":0},{"id":509160,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"通信原理实验","courseno":"1822023","teacherno":"240210","name":"覃远年","term":"2018-2019_2","courseid":"BS0202045X3","croomno":null,"comm":null,"startweek":1,"endweek":1,"oddweek":null,"week":6,"seq":"2","maxcnt":300,"xf":1,"llxs":0,"syxs":16,"sjxs":0,"qtxs":0,"sctcnt":188,"hours":0},{"id":508332,"ctype":"BT","tname":"专业基础必修","examt":"考试","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"微波技术与天线","courseno":"1821920","teacherno":"280067","name":"李晓峰","term":"2018-2019_2","courseid":"BT0200063X0","croomno":"02206Y","comm":null,"startweek":1,"endweek":10,"oddweek":null,"week":3,"seq":"1","maxcnt":100,"xf":3,"llxs":42,"syxs":6,"sjxs":0,"qtxs":0,"sctcnt":97,"hours":0},{"id":508333,"ctype":"BT","tname":"专业基础必修","examt":"考试","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"微波技术与天线","courseno":"1821920","teacherno":"280067","name":"李晓峰","term":"2018-2019_2","courseid":"BT0200063X0","croomno":"02206Y","comm":null,"startweek":1,"endweek":11,"oddweek":null,"week":1,"seq":"2","maxcnt":100,"xf":3,"llxs":42,"syxs":6,"sjxs":0,"qtxs":0,"sctcnt":97,"hours":0},{"id":509153,"ctype":"BS","tname":"实践环节","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"生产实习","courseno":"1822016","teacherno":"280002","name":"刘庆华","term":"2018-2019_2","courseid":"BS0200028X0","croomno":null,"comm":null,"startweek":20,"endweek":20,"oddweek":null,"week":7,"seq":"2","maxcnt":300,"xf":3,"llxs":0,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":173,"hours":0},{"id":507125,"ctype":"BT","tname":"专业基础必修","examt":"考试","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"通信原理A","courseno":"1821359","teacherno":"280098","name":"黎洪松","term":"2018-2019_2","courseid":"BT0200159X0","croomno":"11C107*","comm":null,"startweek":1,"endweek":14,"oddweek":null,"week":1,"seq":"1","maxcnt":80,"xf":3.5,"llxs":56,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":70,"hours":0},{"id":507126,"ctype":"BT","tname":"专业基础必修","examt":"考试","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"通信原理A","courseno":"1821359","teacherno":"280098","name":"黎洪松","term":"2018-2019_2","courseid":"BT0200159X0","croomno":"11C107*","comm":null,"startweek":1,"endweek":14,"oddweek":null,"week":3,"seq":"2","maxcnt":80,"xf":3.5,"llxs":56,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":70,"hours":0},{"id":509855,"ctype":"XZ","tname":"专业限选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"信息论","courseno":"1822047","teacherno":"030134","name":"袁华","term":"2018-2019_2","courseid":"XZ0202064X0","croomno":"11C109*","comm":null,"startweek":9,"endweek":16,"oddweek":null,"week":2,"seq":"3","maxcnt":120,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":118,"hours":0},{"id":509856,"ctype":"XZ","tname":"专业限选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"信息论","courseno":"1822047","teacherno":"030134","name":"袁华","term":"2018-2019_2","courseid":"XZ0202064X0","croomno":"11C109*","comm":null,"startweek":9,"endweek":16,"oddweek":null,"week":4,"seq":"4","maxcnt":120,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":118,"hours":0},{"id":510611,"ctype":"TW","tname":"文化与艺术","examt":"考查","dptname":null,"dptno":"00","spname":null,"spno":"000000","grade":"2018","cname":"世界经济地理之一带一路（网络）","courseno":"1822172","teacherno":"13018","name":"文如冰","term":"2018-2019_2","courseid":"TW0000501X0","croomno":null,"comm":null,"startweek":5,"endweek":5,"oddweek":null,"week":5,"seq":"4","maxcnt":1000,"xf":1,"llxs":16,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":386,"hours":0},{"id":514439,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"000005","grade":"2015","cname":"电磁场","courseno":"1822405","teacherno":"1120192","name":"傅涛","term":"2018-2019_2","courseid":"BT0200010X0","croomno":"02201Y","comm":null,"startweek":6,"endweek":11,"oddweek":null,"week":7,"seq":"1","maxcnt":150,"xf":3,"llxs":46,"syxs":2,"sjxs":0,"qtxs":0,"sctcnt":157,"hours":0},{"id":514440,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"000005","grade":"2015","cname":"电磁场","courseno":"1822405","teacherno":"1120192","name":"傅涛","term":"2018-2019_2","courseid":"BT0200010X0","croomno":"02201Y","comm":null,"startweek":6,"endweek":11,"oddweek":null,"week":7,"seq":"2","maxcnt":150,"xf":3,"llxs":46,"syxs":2,"sjxs":0,"qtxs":0,"sctcnt":157,"hours":0},{"id":514441,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"000005","grade":"2015","cname":"电磁场","courseno":"1822405","teacherno":"1120192","name":"傅涛","term":"2018-2019_2","courseid":"BT0200010X0","croomno":"02201Y","comm":null,"startweek":6,"endweek":11,"oddweek":null,"week":7,"seq":"3","maxcnt":150,"xf":3,"llxs":46,"syxs":2,"sjxs":0,"qtxs":0,"sctcnt":157,"hours":0},{"id":514442,"ctype":"BT","tname":"专业基础必修","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"000005","grade":"2015","cname":"电磁场","courseno":"1822405","teacherno":"1120192","name":"傅涛","term":"2018-2019_2","courseid":"BT0200010X0","croomno":"02201Y","comm":null,"startweek":6,"endweek":10,"oddweek":null,"week":7,"seq":"4","maxcnt":150,"xf":3,"llxs":46,"syxs":2,"sjxs":0,"qtxs":0,"sctcnt":157,"hours":0},{"id":508345,"ctype":"XZ","tname":"专业限选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603","grade":"2016","cname":"嵌入式原理及应用","courseno":"1821927","teacherno":"020531","name":"孙安青","term":"2018-2019_2","courseid":"XZ0202061X1","croomno":"02109Y","comm":null,"startweek":9,"endweek":16,"oddweek":null,"week":1,"seq":"4","maxcnt":150,"xf":2,"llxs":16,"syxs":16,"sjxs":0,"qtxs":0,"sctcnt":155,"hours":0},{"id":515075,"ctype":"XZ","tname":"专业限选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603Z","grade":"2016","cname":"机器学习","courseno":"1822573","teacherno":"020522","name":"林乐平","term":"2018-2019_2","courseid":"XZ0200180X0","croomno":"02304Y","comm":null,"startweek":9,"endweek":16,"oddweek":null,"week":2,"seq":"2","maxcnt":40,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":40,"hours":0},{"id":515076,"ctype":"XZ","tname":"专业限选","examt":"考查","dptname":null,"dptno":"2","spname":null,"spno":"080603Z","grade":"2016","cname":"机器学习","courseno":"1822573","teacherno":"020522","name":"林乐平","term":"2018-2019_2","courseid":"XZ0200180X0","croomno":"02306Y","comm":null,"startweek":9,"endweek":16,"oddweek":null,"week":5,"seq":"2","maxcnt":40,"xf":2,"llxs":32,"syxs":0,"sjxs":0,"qtxs":0,"sctcnt":40,"hours":0}]
     */

    private boolean success;
    private int total;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 508342
         * ctype : RZ
         * tname : 专业任选
         * examt : 考查
         * dptname : null
         * dptno : 2
         * spname : null
         * spno : 080603
         * grade : 2016
         * cname : RFID技术与应用
         * courseno : 1821925
         * teacherno : 020409
         * name : 李天松
         * term : 2018-2019_2
         * courseid : RZ0200145X0
         * croomno : 17412*
         * comm : null
         * startweek : 10
         * endweek : 16
         * oddweek : null
         * week : 1
         * seq : 3
         * maxcnt : 150
         * xf : 2.0
         * llxs : 28.0
         * syxs : 4.0
         * sjxs : 0.0
         * qtxs : 0.0
         * sctcnt : 150
         * hours : 0
         */

        private int id;
        private String ctype;
        private String tname;
        private String examt;
        private Object dptname;
        private String dptno;
        private Object spname;
        private String spno;
        private String grade;
        private String cname;
        private String courseno;
        private String teacherno;
        private Object name;
        private Object term;
        private String courseid;
        private Object croomno;
        private Object comm;
        private int startweek;
        private int endweek;
        private Object oddweek;
        private int week;
        private String seq;
        private int maxcnt;
        private double xf;
        private double llxs;
        private double syxs;
        private double sjxs;
        private double qtxs;
        private int sctcnt;
        private int hours;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCtype() {
            return ctype;
        }

        public void setCtype(String ctype) {
            this.ctype = ctype;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }

        public String getExamt() {
            return examt;
        }

        public void setExamt(String examt) {
            this.examt = examt;
        }

        public Object getDptname() {
            return dptname;
        }

        public void setDptname(Object dptname) {
            this.dptname = dptname;
        }

        public String getDptno() {
            return dptno;
        }

        public void setDptno(String dptno) {
            this.dptno = dptno;
        }

        public Object getSpname() {
            return spname;
        }

        public void setSpname(Object spname) {
            this.spname = spname;
        }

        public String getSpno() {
            return spno;
        }

        public void setSpno(String spno) {
            this.spno = spno;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCourseno() {
            return courseno;
        }

        public void setCourseno(String courseno) {
            this.courseno = courseno;
        }

        public String getTeacherno() {
            return teacherno;
        }

        public void setTeacherno(String teacherno) {
            this.teacherno = teacherno;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getTerm() {
            return term;
        }

        public void setTerm(Object term) {
            this.term = term;
        }

        public String getCourseid() {
            return courseid;
        }

        public void setCourseid(String courseid) {
            this.courseid = courseid;
        }

        public Object getCroomno() {
            return croomno;
        }

        public void setCroomno(Object croomno) {
            this.croomno = croomno;
        }

        public Object getComm() {
            return comm;
        }

        public void setComm(Object comm) {
            this.comm = comm;
        }

        public int getStartweek() {
            return startweek;
        }

        public void setStartweek(int startweek) {
            this.startweek = startweek;
        }

        public int getEndweek() {
            return endweek;
        }

        public void setEndweek(int endweek) {
            this.endweek = endweek;
        }

        public Object getOddweek() {
            return oddweek;
        }

        public void setOddweek(Object oddweek) {
            this.oddweek = oddweek;
        }

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public int getMaxcnt() {
            return maxcnt;
        }

        public void setMaxcnt(int maxcnt) {
            this.maxcnt = maxcnt;
        }

        public double getXf() {
            return xf;
        }

        public void setXf(double xf) {
            this.xf = xf;
        }

        public double getLlxs() {
            return llxs;
        }

        public void setLlxs(double llxs) {
            this.llxs = llxs;
        }

        public double getSyxs() {
            return syxs;
        }

        public void setSyxs(double syxs) {
            this.syxs = syxs;
        }

        public double getSjxs() {
            return sjxs;
        }

        public void setSjxs(double sjxs) {
            this.sjxs = sjxs;
        }

        public double getQtxs() {
            return qtxs;
        }

        public void setQtxs(double qtxs) {
            this.qtxs = qtxs;
        }

        public int getSctcnt() {
            return sctcnt;
        }

        public void setSctcnt(int sctcnt) {
            this.sctcnt = sctcnt;
        }

        public int getHours() {
            return hours;
        }

        public void setHours(int hours) {
            this.hours = hours;
        }
    }
}
