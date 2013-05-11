/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.Admin;
import Sadna.Client.Member;
import Sadna.Client.Moderator;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.SubForum;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.DefaultListModel;

/**
 *
 * @author fistuk
 */
public class ForumPage extends javax.swing.JFrame {

    /**
     * Creates new form ForumPage
     */
    public ForumPage() {
        initComponents();
        addNewSubButton.setVisible(false);
        jLabelHeadNewSubForum.setVisible(false);
        jLabelNewSubName.setVisible(false);
        jTextAreaListOfModerators.setVisible(false);
        jTextFieldNewSubName.setVisible(false);
        jLabelEnterModerators.setVisible(false);
        jButtonToAdminPage.setVisible(false);
        if (CurrentStatus.currUser instanceof Admin) {
            jButtonToAdminPage.setVisible(true);
        }

        if (CurrentStatus.currUser instanceof Moderator) {
            addNewSubButton.setVisible(true);
            jLabelNewSubName.setVisible(true);
            jTextAreaListOfModerators.setVisible(true);
            jTextFieldNewSubName.setVisible(true);
            jLabelEnterModerators.setVisible(true);
        }
        if (CurrentStatus.currUser instanceof Member) {
            logInButton.setVisible(false);
            registerButton.setVisible(false);
        } else {
            jButtonSignout.setVisible(false);
        }

        this.setResizable(false);
        jLabel.setText("Forum: " + CurrentStatus.currForum.getForumName());
        Forum f = CurrentStatus.currForum;
        DefaultListModel listModel = new DefaultListModel();
        List<SubForum> listOfSubForums;
        listOfSubForums = CurrentStatus.currUser.viewSubForums(f.getForumName());
        for (SubForum sf : listOfSubForums) {
            listModel.addElement(sf.getSubForumName());
        }
        subForumsList.setModel(listModel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        subForumsList = new javax.swing.JList();
        logInButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        enterSubForumButton = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonSignout = new javax.swing.JButton();
        jTextFieldNewSubName = new javax.swing.JTextField();
        jLabelNewSubName = new javax.swing.JLabel();
        jLabelHeadNewSubForum = new javax.swing.JLabel();
        addNewSubButton = new javax.swing.JButton();
        jLabelEnterModerators = new javax.swing.JLabel();
        jTextAreaListOfModerators = new javax.swing.JTextArea();
        jButtonToAdminPage = new javax.swing.JButton();
        getNotificationsButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N

        subForumsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of sub forums", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(subForumsList);

        logInButton.setText("log in");
        logInButton.setToolTipText("log in with an existing member");
        logInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logInButtonActionPerformed(evt);
            }
        });

        registerButton.setText("register");
        registerButton.setToolTipText("registers as a new member to the forum");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        enterSubForumButton.setText("Enter Sub-Forum");
        enterSubForumButton.setToolTipText("enters the selected sub forum");
        enterSubForumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterSubForumButtonActionPerformed(evt);
            }
        });

        jButtonBack.setText("back");
        jButtonBack.setToolTipText("back to the main page");
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        jButtonSignout.setText("sign out");
        jButtonSignout.setToolTipText("sign out to a user mode");
        jButtonSignout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignoutActionPerformed(evt);
            }
        });

        jLabelNewSubName.setText("subForumName");

        jLabelHeadNewSubForum.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelHeadNewSubForum.setText("New SubForum");

        addNewSubButton.setText("Add sub-forum");
        addNewSubButton.setToolTipText("adding a new sub forum\nmake sure the name is unique\nand the moderators exist");
        addNewSubButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewSubButtonActionPerformed(evt);
            }
        });

        jLabelEnterModerators.setText("enter moderators");

        jTextAreaListOfModerators.setColumns(20);
        jTextAreaListOfModerators.setLineWrap(true);
        jTextAreaListOfModerators.setRows(5);

        jButtonToAdminPage.setText("admin page");
        jButtonToAdminPage.setToolTipText("a link to the admin page");
        jButtonToAdminPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonToAdminPageActionPerformed(evt);
            }
        });

        getNotificationsButton.setText("get notifications");
        getNotificationsButton.setToolTipText("get all the unread notifications");
        getNotificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNotificationsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(163, 163, 163))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(addNewSubButton)
                                .addGap(45, 45, 45))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSignout)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(getNotificationsButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logInButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(registerButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enterSubForumButton))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelNewSubName)
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextFieldNewSubName, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jTextAreaListOfModerators, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonToAdminPage, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelHeadNewSubForum, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelEnterModerators, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonToAdminPage, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelHeadNewSubForum, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelEnterModerators)
                        .addGap(18, 18, 18)
                        .addComponent(jTextAreaListOfModerators, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNewSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldNewSubName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addNewSubButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logInButton)
                    .addComponent(registerButton)
                    .addComponent(enterSubForumButton)
                    .addComponent(jButtonBack)
                    .addComponent(jButtonSignout)
                    .addComponent(getNotificationsButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        LogInPage logInPage = new LogInPage();
        this.setVisible(false);
        this.dispose();
        logInPage.setVisible(true);
    }//GEN-LAST:event_logInButtonActionPerformed

    private void enterSubForumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterSubForumButtonActionPerformed
        String selectedValue = (String) subForumsList.getSelectedValue();
        if (selectedValue == null) {
            return;
        }
        String forumName = CurrentStatus.currForum.getForumName();
        SubForum subForum = CurrentStatus.currUser.getSubForum(forumName, selectedValue);
        CurrentStatus.currSubForum = subForum;
        SubForumPage subForumPage = new SubForumPage();
        this.setVisible(false);
        this.dispose();
        subForumPage.setVisible(true);
    }//GEN-LAST:event_enterSubForumButtonActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        Member m = (Member) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String userName = m.getUserName();
//        User logout = m.logout(forumName, userName);
//        CurrentStatus.currUser = logout;
        CurrentStatus.currUser = null;
        CurrentStatus.currForum = null;
        MainFrame mainFrame = new MainFrame();
        this.setVisible(false);
        this.dispose();
        mainFrame.setVisible(true);
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        RegistrationPage registrationPage = new RegistrationPage();
        this.setVisible(false);
        this.dispose();
        registrationPage.setVisible(true);
    }//GEN-LAST:event_registerButtonActionPerformed

    private void jButtonSignoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignoutActionPerformed
        Member m = (Member) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String userName = m.getUserName();
        User logout = m.logout(forumName);
        CurrentStatus.currUser = logout;
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);
    }//GEN-LAST:event_jButtonSignoutActionPerformed

    private void addNewSubButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewSubButtonActionPerformed
        String text = jTextAreaListOfModerators.getText();
        StringTokenizer stringTokenizer = new StringTokenizer(text, "\n");
        ArrayList<Moderator> arrayList = new ArrayList<Moderator>();
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            arrayList.add(new Moderator(nextToken, null, null, null, null));
        }
        String newSubForumName = jTextFieldNewSubName.getText();
        SubForum subForum = new SubForum(CurrentStatus.currForum, newSubForumName);
        Admin admin = (Admin) CurrentStatus.currUser;
        admin.addSubForum(subForum, arrayList);
        jTextAreaListOfModerators.setText("");
        jLabelEnterModerators.setText("");
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);

    }//GEN-LAST:event_addNewSubButtonActionPerformed

    private void jButtonToAdminPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonToAdminPageActionPerformed
        AdminPage adminPage = new AdminPage();
        this.setVisible(false);
        this.dispose();
        adminPage.setVisible(true);
    }//GEN-LAST:event_jButtonToAdminPageActionPerformed

    private void getNotificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNotificationsButtonActionPerformed
        NotificationsPage notificationsPage = new NotificationsPage();
        notificationsPage.setVisible(true);
    }//GEN-LAST:event_getNotificationsButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNewSubButton;
    private javax.swing.JButton enterSubForumButton;
    private javax.swing.JButton getNotificationsButton;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonSignout;
    private javax.swing.JButton jButtonToAdminPage;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabelEnterModerators;
    private javax.swing.JLabel jLabelHeadNewSubForum;
    private javax.swing.JLabel jLabelNewSubName;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaListOfModerators;
    private javax.swing.JTextField jTextFieldNewSubName;
    private javax.swing.JButton logInButton;
    private javax.swing.JButton registerButton;
    private javax.swing.JList subForumsList;
    // End of variables declaration//GEN-END:variables
}
