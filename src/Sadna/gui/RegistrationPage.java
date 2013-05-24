/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.Member;
import javax.swing.JFrame;

/**
 *
 * @author fistuk
 */
public class RegistrationPage extends ForumJFrame {

    /**
     * Creates new form RegistrationPage
     */
    public RegistrationPage() {
        initComponents();
        this.setResizable(false);
        jLabelInvalidData.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTitle = new javax.swing.JLabel();
        jLabelUserName = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jButtonRegister = new javax.swing.JButton();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabelInvalidData = new javax.swing.JLabel();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitle.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabelTitle.setText("Registration");

        jLabelUserName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelUserName.setText("UserName ");

        jLabelPassword.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelPassword.setText("Password");

        jButtonRegister.setText("Register");
        jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegisterActionPerformed(evt);
            }
        });

        jLabelEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabelEmail.setText("Email");

        jLabelInvalidData.setForeground(new java.awt.Color(255, 0, 0));
        jLabelInvalidData.setText("Invalid data - ");

        jButtonBack.setText("back");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(183, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(161, 161, 161))
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelInvalidData, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUserName)
                            .addComponent(jLabelPassword)
                            .addComponent(jLabelEmail)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jButtonBack)))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldUserName)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(jTextFieldEmail)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jButtonRegister)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUserName)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRegister)
                    .addComponent(jButtonBack))
                .addGap(18, 18, 18)
                .addComponent(jLabelInvalidData)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegisterActionPerformed
        String userNameStr = jTextFieldUserName.getText();
        String passwordStr = jPasswordField1.getText();
        String email = jTextFieldEmail.getText();
        if (userNameStr.equals("") || passwordStr.equals("") || email.equals("")) {
            jTextFieldUserName.setText("");
            jTextFieldEmail.setText("");
            jPasswordField1.setText("");
            jLabelInvalidData.setVisible(true);
            return;
        }
        String forumName = CurrentStatus.currForum.getForumName();
        Member register = CurrentStatus.currUser.register(forumName,
                userNameStr, passwordStr, email);
        if (register == null) {
            jTextFieldUserName.setText("");
            jTextFieldEmail.setText("");
            jPasswordField1.setText("");
            jLabelInvalidData.setVisible(true);
            return;
        }
        CurrentStatus.currUser = register;
        goBack();
    }//GEN-LAST:event_jButtonRegisterActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        goBack();
    }//GEN-LAST:event_jButtonBackActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonRegister;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelInvalidData;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUserName;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldUserName;
    // End of variables declaration//GEN-END:variables

    private void goBack() {
        EnumPages whereToGo = null;
        ForumJFrame frame = null;
        for (int i = 0; i < 1; i++) {
            if (CurrentStatus.currForum == null) {
                whereToGo = EnumPages.MAIN;
                break;
            }
            if (CurrentStatus.currSubForum == null) {
                whereToGo = EnumPages.FORUM;
                break;
            }
            if (CurrentStatus.currThread == null) {
                whereToGo = EnumPages.SUBFORUM;
                break;
            }
            whereToGo = EnumPages.THREAD;
        }
        switch (whereToGo) {
            case MAIN:
                frame = new MainFrame();
                break;
            case FORUM:
                frame = new ForumPage();
                break;
            case SUBFORUM:
                frame = new SubForumPage();
                break;
            case THREAD:
                frame = new ThreadPage();
                break;

        }
        this.setVisible(false);
        this.dispose();
        frame.setVisible(true);
        CurrentStatus.currFrame = frame;
    }
}
