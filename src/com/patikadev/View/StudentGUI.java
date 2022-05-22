package com.patikadev.View;

import com.patikadev.Model.*;
import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class StudentGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_stu_top;
    private JLabel lbl_stu_welcome;
    private JTabbedPane tab_student;
    private JPanel pnl_stu_patika_list;
    private JScrollPane scrl_stu_patika_list;
    private JTable tbl_stu_patika_list;
    private JButton btn_logout;
    private JPanel pnl_stu_patika_form;
    private JTextField fld_stu_patika_id;
    private JButton btn_stu_get_course_list;
    private JPanel pnl_rgstr_course_list;
    private JScrollPane scr_rgstr_course_list;
    private JTable tbl_rgstr_course_list;
    private JPanel pnl_rgstr_course_refresh;
    private JButton btn_rgstr_course_refresh;
    private JPanel pnl_stu_content_list;
    private JScrollPane scrl_stu_content_list;
    private JTable tbl_stu_content_list;
    private JPanel pnl_content_top;
    private JTextField fld_review_content_ID;
    private JTextField fld_review_content;
    private JButton btn_review_add;
    private JTextField fld_review_content_topic;
    private JPanel pnl_stu_quiz_list;
    private JScrollPane scrl_stu_quiz_list;
    private JTable tbl_stu_quiz_list;
    private JPanel pnl_answer_bottom;
    private JTextField fld_answer;
    private JButton btn_answer_add;
    private JPanel pnl_answer_quiz;
    private JTable tbl_answer_quiz;
    private JScrollPane scrl_answer_quiz;
    ArrayList<Integer> courseID = new ArrayList<>();
    ArrayList<Integer> contentID = new ArrayList<>();
    ArrayList<Integer> questionID = new ArrayList<>();

    DefaultTableModel mdl_patika_list = new DefaultTableModel();
    private Object[] row_patika_list;

    DefaultTableModel mdl_rgstr_course_list = new DefaultTableModel();
    private Object[] row_rgstr_course_list;

    DefaultTableModel mdl_rgstr_content_list = new DefaultTableModel();
    private Object[] row_rgstr_content_list;

    DefaultTableModel mdl_stu_quiz_list = new DefaultTableModel();
    private Object[] row_stu_quiz_list;

    DefaultTableModel mdl_answer_quiz_list = new DefaultTableModel();
    private Object[] row_answer_quiz_list;
    private String select_question;
    private int select_question_id;
    private int select_content_id;

    private final Student student;

    public StudentGUI(Student student){
        this.student = student;
        add(wrapper);
        setSize(1000, 500);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        lbl_stu_welcome.setText("Hoş Geldin " + student.getName());

//patika listesi kodları başlangıcı
        mdl_patika_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1){
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        tbl_stu_patika_list.setModel(mdl_patika_list);
        tbl_stu_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_stu_patika_list.getColumnModel().getColumn(0).setMaxWidth(75 );

//üzerine tıklanan patikanın id sini ders listele butonu üzerindeki patika ID kutucuğuna yazdırma
        tbl_stu_patika_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_patika_id = tbl_stu_patika_list.getValueAt(tbl_stu_patika_list.getSelectedRow(),0).toString();
                fld_stu_patika_id.setText(select_patika_id);
            }
            catch (Exception exception){

            }
        });
//patika listesi kodları bitişi

//kayıtlı derslerim listesi kodları başlangıcı
        mdl_rgstr_course_list = new DefaultTableModel();
        Object[] col_courseList = {"ID", "Dersin Adı", "Programlama Dili", "Patika", "Eğitmen"};
        mdl_rgstr_course_list.setColumnIdentifiers(col_courseList);
        row_rgstr_course_list = new Object[col_courseList.length];
        loadRgstrCourseModel();
        tbl_rgstr_course_list.setModel(mdl_rgstr_course_list);
        tbl_rgstr_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_rgstr_course_list.getTableHeader().setReorderingAllowed(false);
//kayıtlı derslerim listesi kodları bitişi

//İçerikler sekmesi kodlarının başlangıcı
        mdl_rgstr_content_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_edu_content_list = {"ID", "İçerik Başlığı", "Açıklama", "Ders Adı", "YouTube Linki"};
        mdl_rgstr_content_list.setColumnIdentifiers(col_edu_content_list);
        row_rgstr_content_list = new Object[col_edu_content_list.length];
        loadStuContentModel();
        tbl_stu_content_list .setModel(mdl_rgstr_content_list);
        tbl_stu_content_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_stu_content_list.getTableHeader().setReorderingAllowed(false);


//üzerine tıklanan içeriğin id sini değerlendirme kutucuğuna yazdırma
        tbl_stu_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_content_id = tbl_stu_content_list.getValueAt(tbl_stu_content_list.getSelectedRow(),0).toString();
                fld_review_content_ID.setText(select_content_id);
            }
            catch (Exception exception){

            }
        });

//üzerine tıklanan içeriğin başlığını değerlendirme kutucuğuna yazdırma
        tbl_stu_content_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_content_id = tbl_stu_content_list.getValueAt(tbl_stu_content_list.getSelectedRow(),1).toString();
                fld_review_content_topic.setText(select_content_id);
            }
            catch (Exception exception){

            }
        });
//İçerikler sekmesi kodlarının bitişi

///Quiz soruları sekmesi kodları başlangıcı
        mdl_stu_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_edu_quiz_list = {"ID", "Quiz Sorusu", "İçerik Başlığı", "İçerik ID"};
        mdl_stu_quiz_list.setColumnIdentifiers(col_edu_quiz_list);
        row_stu_quiz_list = new Object[col_edu_quiz_list.length];
        loadStuQuizModel();
        tbl_stu_quiz_list.setModel(mdl_stu_quiz_list);
        tbl_stu_quiz_list.getColumnModel().getColumn(0).setMaxWidth(50);
        //tbl_edu_quiz_list.getColumnModel().getColumn(2).setMaxWidth(300);
        tbl_stu_quiz_list.getColumnModel().getColumn(3).setMaxWidth(100);
        tbl_stu_quiz_list.getTableHeader().setReorderingAllowed(false);

        tbl_stu_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                select_question = tbl_stu_quiz_list.getValueAt(tbl_stu_quiz_list.getSelectedRow(),1).toString();
                select_question_id = Integer.parseInt(tbl_stu_quiz_list.getValueAt(tbl_stu_quiz_list.getSelectedRow(),0).toString());
                select_content_id = Integer.parseInt(tbl_stu_quiz_list.getValueAt(tbl_stu_quiz_list.getSelectedRow(),3).toString());
            }
            catch (Exception exception){

            }
        });
//Quiz soruları sekmesi kodları bitişi

//Quiz cevapları sekmesi kodları başlangıcı
        mdl_answer_quiz_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 )
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_stu_answer_quiz_list = {"ID", "Quiz Sorusu", "Cevaplarınız"};
        mdl_answer_quiz_list.setColumnIdentifiers(col_stu_answer_quiz_list);
        row_answer_quiz_list = new Object[col_edu_quiz_list.length];
        loadStuAnsQuizModel();
        tbl_answer_quiz.setModel(mdl_answer_quiz_list);
        tbl_answer_quiz.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_answer_quiz.getTableHeader().setReorderingAllowed(false);
//Quiz cevapları sekmesi kodları bitişi

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

        btn_stu_get_course_list.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_stu_patika_id)){
                Helper.showMsg("fill");
            }
            else{
                int patika_id = Integer.parseInt(fld_stu_patika_id.getText());
                GetCourseStudentGUI cou = new GetCourseStudentGUI(Integer.parseInt(fld_stu_patika_id.getText()), student);

                cou.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        loadRgstrCourseModel();
                        loadStuContentModel();
                        loadStuQuizModel();
                        loadStuAnsQuizModel();
                    }
                });
            }
        });
        btn_rgstr_course_refresh.addActionListener(e -> {
            loadRgstrCourseModel();
            loadStuContentModel();
            loadStuQuizModel();
            loadStuAnsQuizModel();
        });

//içeriklere yorum ve değerlendirme ekleme. bunlar operator tarafından görülebilir olacak
        btn_review_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_review_content_ID) || Helper.isFieldEmpty(fld_review_content_topic) || Helper.isFieldEmpty(fld_review_content)){
                Helper.showMsg("fill");
            }
            else{
                if (ReviewContent.add(fld_review_content.getText(), fld_review_content_topic.getText(), Integer.parseInt(fld_review_content_ID.getText()))){
                    Helper.showMsg("done");
                    fld_review_content_ID.setText(null);
                    fld_review_content_topic.setText(null);
                    fld_review_content.setText(null);
                    loadStuContentModel();
                }
                else {
                    Helper.showMsg("error");
                }
            }
        });

//quiz sorularına cevap ekleme
        btn_answer_add.addActionListener(e -> {
            ArrayList<Integer> list = new ArrayList<>();
            if (Helper.isFieldEmpty(fld_answer)){
                Helper.showMsg("fill");
            }
            else if(select_question_id == 0 || select_question == null || select_content_id == 0){
                Helper.showMsg("Cevaplayacağınız soruyu seçiniz.");
            }
            else{
                for (AnswerQuiz c : AnswerQuiz.getList()) { //var olan cevabı tekrar eklememek için yazıldı
                    list.add(c.getQuestion_id());
                }
                if (!list.contains(select_question_id)){
                    if (AnswerQuiz.add(select_question, fld_answer.getText(), select_question_id, select_content_id)){
                        Helper.showMsg("done");
                        loadStuAnsQuizModel();
                        fld_answer.setText(null);
                    }
                    else {
                        Helper.showMsg("error");
                    }
                }
                else if (list.contains(select_question_id)){
                    Helper.showMsg("Bu soruyu daha önce cevapladınız");
                }
            }

        });
    }

    private void loadRgstrCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_rgstr_course_list.getModel();
        clearModel.setRowCount(0);
        ArrayList<Integer> list = new ArrayList<>();
        for (RegisteredCourse c : RegisteredCourse.getList()){
            if (student.getId() == c.getStudent_id()){
                list.add(c.getCourse_id());
            }
        }
        int i;
        for (Course obj : Course.getList()){

            if (list.contains(obj.getId())){
                i = 0;
                row_rgstr_course_list[i++] = obj.getId();
                courseID.add(obj.getId());      //student ın derslerine ait contentleri listelemek için courseID arraylisti kullanıldı
                row_rgstr_course_list[i++] = obj.getName();
                row_rgstr_course_list[i++] = obj.getLang();
                row_rgstr_course_list[i++] = obj.getPatika().getName();
                row_rgstr_course_list[i++] = obj.getEducator().getName();
                mdl_rgstr_course_list.addRow(row_rgstr_course_list);
            }
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_stu_patika_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    private void loadStuContentModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_stu_content_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Content obj : Content.getList()){
            if (courseID.contains(obj.getCourse_id())){  //student ın derslerine ait contentleri sorgulama
                i=0;
                row_rgstr_content_list[i++] = obj.getId();
                contentID.add(obj.getId());    //student ın kendi içeriklerinin quiz sorularını göstermek için yazıldı
                row_rgstr_content_list[i++] = obj.getTopic();
                row_rgstr_content_list[i++] = obj.getExplanation();
                row_rgstr_content_list[i++] = obj.getCourse().getName();
                row_rgstr_content_list[i++] = obj.getYtubeUrl();
                mdl_rgstr_content_list.addRow(row_rgstr_content_list);
            }
        }
    }

    private void loadStuQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_stu_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj : Quiz.getList()){
            if (contentID.contains(obj.getContent_id())){
                i = 0;
                row_stu_quiz_list[i++] = obj.getId();
                questionID.add(obj.getId());
                row_stu_quiz_list[i++] = obj.getQuestion();
                row_stu_quiz_list[i++] = obj.getContent_topic();
                row_stu_quiz_list[i++] = obj.getContent_id();
                mdl_stu_quiz_list.addRow(row_stu_quiz_list);
            }
        }
    }

    private void loadStuAnsQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_answer_quiz.getModel();
        clearModel.setRowCount(0);
        int i;
        for (AnswerQuiz obj : AnswerQuiz.getList()){
            if (questionID.contains(obj.getQuestion_id())) {
                i = 0;
                row_answer_quiz_list[i++] = obj.getId();
                row_answer_quiz_list[i++] = obj.getQuestion();
                row_answer_quiz_list[i++] = obj.getAnswer();
                mdl_answer_quiz_list.addRow(row_answer_quiz_list);
            }
        }
    }
}
