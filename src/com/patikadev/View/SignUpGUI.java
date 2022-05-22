package com.patikadev.View;

import com.patikadev.Model.User;
import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_sign_form;
    private JTextField fld_sign_name;
    private JTextField fld_sign_uname;
    private JTextField fld_sign_pass;
    private JTextField fld_sign_type;
    private JButton btn_sign;
    private JButton btn_back;

    public SignUpGUI(){
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenCenterPoint("x" , getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_sign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_sign_name) || Helper.isFieldEmpty(fld_sign_uname) || Helper.isFieldEmpty(fld_sign_pass)){
                    Helper.showMsg("fill");
                }
                else{
                    String name = fld_sign_name.getText();
                    String uname = fld_sign_uname.getText();
                    String pass = fld_sign_pass.getText();
                    String type = fld_sign_type.getText();
                    if (User.add(name, uname, pass, type)){
                        Helper.showMsg("done");
                        fld_sign_name.setText(null);
                        fld_sign_uname.setText(null);
                        fld_sign_pass.setText(null);
                    }
                }

            }
        });
        btn_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
