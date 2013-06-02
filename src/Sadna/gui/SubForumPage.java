/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.Member;
import Sadna.Client.User;
import Sadna.db.Forum;
import Sadna.db.Post;
import Sadna.db.SubForum;
import Sadna.db.ThreadMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class SubForumPage extends ForumJFrame {

    private ThreadMessage currTM;

    /**
     * Creates new form SubForumPage
     */
    public SubForumPage() {
        initComponents();
        this.setResizable(false);
        this.jLabelErrorCannotDelete.setVisible(false);
        this.jLabelError.setVisible(false);
        this.jButtonEdit.setVisible(false);
        if (CurrentStatus.currUser instanceof Member) {
            logInButton.setVisible(false);
            registerButton.setVisible(false);
            Member m = (Member) CurrentStatus.currUser;
            boolean hasNotifications = m.hasNotifications();
            if (hasNotifications) {
                this.getNotificationsButton.setBackground(Color.RED);
            }
        } else {
            getNotificationsButton.setVisible(false);
            jButtonSignout.setVisible(false);
            jTextFieldAddContent.setVisible(false);
            jTextFieldAddTitle.setVisible(false);
            jButtonPublisThread.setVisible(false);
            this.jButtonDeleteThread.setVisible(false);
            this.jButtonEditThread.setVisible(false);
            this.jButtonEdit.setVisible(false);
        }
        String subForumName = CurrentStatus.currSubForum.getSubForumName();
        jLabelTitle.setText("Welcome To The Sub-Forum: " + subForumName);
        updateListOfThreads();

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
        jScrollPane2 = new javax.swing.JScrollPane();
        jListThreads = new javax.swing.JList();
        jButtonEnterThread = new javax.swing.JButton();
        logInButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jButtonSignout = new javax.swing.JButton();
        jTextFieldAddTitle = new javax.swing.JTextField();
        jButtonPublisThread = new javax.swing.JButton();
        jLabelError = new javax.swing.JLabel();
        jTextFieldAddContent = new javax.swing.JTextField();
        jButtonDeleteThread = new javax.swing.JButton();
        jLabelErrorCannotDelete = new javax.swing.JLabel();
        getNotificationsButton = new javax.swing.JButton();
        jButtonNotifyMainThread = new javax.swing.JButton();
        jButtonEdit = new javax.swing.JButton();
        jButtonEditThread = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelTitle.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        jLabelTitle.setText("Welcome to...");

        jListThreads.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "List of Threads", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jListThreads);

        jButtonEnterThread.setText("Enter Thread");
        jButtonEnterThread.setToolTipText("enters the selected thread");
        jButtonEnterThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnterThreadActionPerformed(evt);
            }
        });

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

        jButtonBack.setText("back");
        jButtonBack.setToolTipText("back to the forum page");
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

        jTextFieldAddTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAddTitleActionPerformed(evt);
            }
        });

        jButtonPublisThread.setText("publish thread");
        jButtonPublisThread.setToolTipText("adds a new thread to the current sub forum");
        jButtonPublisThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPublisThreadActionPerformed(evt);
            }
        });

        jLabelError.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError.setText("Error - didn't publish");

        jButtonDeleteThread.setText("delete thread");
        jButtonDeleteThread.setToolTipText("delete the selected thread\nauthorized only by the publisher, \n moderator or the admin");
        jButtonDeleteThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteThreadActionPerformed(evt);
            }
        });

        jLabelErrorCannotDelete.setForeground(new java.awt.Color(255, 0, 0));
        jLabelErrorCannotDelete.setText("Error - cannot delete");

        getNotificationsButton.setText("get notifications");
        getNotificationsButton.setToolTipText("get all the unread notifications");
        getNotificationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNotificationsButtonActionPerformed(evt);
            }
        });

        jButtonNotifyMainThread.setText("jButton1");
        jButtonNotifyMainThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNotifyMainThreadActionPerformed(evt);
            }
        });

        jButtonEdit.setText("edit");
        jButtonEdit.setToolTipText("delete the selected post\nauthorized only by the publisher, \nmoderator or the admin");
        jButtonEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditActionPerformed(evt);
            }
        });

        jButtonEditThread.setText("edit thread");
        jButtonEditThread.setToolTipText("edit the selected post\nauthorized only by the publisher, \nmoderator or the admin");
        jButtonEditThread.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditThreadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(77, 77, 77))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAddTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButtonPublisThread)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(jButtonEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabelError))))
                            .addComponent(jTextFieldAddContent, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelErrorCannotDelete)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonBack)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonSignout)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonDeleteThread)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(logInButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getNotificationsButton)
                        .addGap(10, 10, 10)
                        .addComponent(registerButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnterThread)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditThread)
                        .addGap(155, 155, 155))))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonNotifyMainThread, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonNotifyMainThread, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 99, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldAddTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldAddContent, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelError)
                        .addGap(9, 9, 9)
                        .addComponent(jButtonEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonPublisThread)
                            .addComponent(jLabelErrorCannotDelete))
                        .addGap(3, 3, 3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEnterThread)
                    .addComponent(registerButton)
                    .addComponent(logInButton)
                    .addComponent(jButtonSignout)
                    .addComponent(jButtonBack)
                    .addComponent(jButtonDeleteThread)
                    .addComponent(getNotificationsButton)
                    .addComponent(jButtonEditThread))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        CurrentStatus.currSubForum = null;
        ForumPage forumPage = new ForumPage();
        this.setVisible(false);
        this.dispose();
        forumPage.setVisible(true);
        CurrentStatus.currFrame = forumPage;
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
        RegistrationPage registrationPage = new RegistrationPage();
        this.setVisible(false);
        this.dispose();
        registrationPage.setVisible(true);
        CurrentStatus.currFrame = registrationPage;
    }//GEN-LAST:event_registerButtonActionPerformed

    private void logInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logInButtonActionPerformed
        LogInPage logInPage = new LogInPage();
        this.setVisible(false);
        this.dispose();
        logInPage.setVisible(true);
        CurrentStatus.currFrame = logInPage;
    }//GEN-LAST:event_logInButtonActionPerformed

    private void jButtonEnterThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnterThreadActionPerformed
        ThreadMessage selectedValue = (ThreadMessage) jListThreads.getSelectedValue();

        String forumName = CurrentStatus.currForum.getForumName();
        String subForumName = CurrentStatus.currSubForum.getSubForumName();
        ThreadMessage thread = CurrentStatus.currUser.getThread(forumName,
                subForumName, selectedValue.getId());
        CurrentStatus.currThread = thread;
        ThreadPage threadPage = new ThreadPage();
        this.setVisible(false);
        this.dispose();
        threadPage.setVisible(true);
        CurrentStatus.currFrame = threadPage;
    }//GEN-LAST:event_jButtonEnterThreadActionPerformed

    private void jButtonSignoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignoutActionPerformed
        Member m = (Member) CurrentStatus.currUser;
        String forumName = CurrentStatus.currForum.getForumName();
        String userName = m.getUserName();
        User logout = m.logout(forumName);
        CurrentStatus.currUser = logout;
        SubForumPage subForumPage = new SubForumPage();
        this.setVisible(false);
        this.dispose();
        subForumPage.setVisible(true);
        CurrentStatus.currFrame = subForumPage;
    }//GEN-LAST:event_jButtonSignoutActionPerformed

    private void jButtonPublisThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPublisThreadActionPerformed
        String title = jTextFieldAddTitle.getText();
        String content = jTextFieldAddContent.getText();
        Member m = (Member) CurrentStatus.currUser;
        SubForum sf = CurrentStatus.currSubForum;
        ThreadMessage threadMessage = new ThreadMessage(sf, title, content, m.getUserName());
        boolean publishThread = m.publishThread(threadMessage);
        if (!publishThread) {
            this.jLabelError.setVisible(true);
            return;
        }
        SubForumPage subForumPage = new SubForumPage();
        this.setVisible(false);
        this.dispose();
        subForumPage.setVisible(true);
        CurrentStatus.currFrame = subForumPage;
    }//GEN-LAST:event_jButtonPublisThreadActionPerformed

    private void jTextFieldAddTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAddTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAddTitleActionPerformed

    private void jButtonDeleteThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteThreadActionPerformed
        ThreadMessage selectedTM = (ThreadMessage) this.jListThreads.getSelectedValue();
        if (selectedTM == null) {
            return;
        }
        Member currMember = (Member) CurrentStatus.currUser;
        boolean deleteThread = currMember.deleteThread(selectedTM);
        if (!deleteThread) {
            this.jLabelErrorCannotDelete.setVisible(true);
        } else {
            SubForumPage subForumPage = new SubForumPage();
            this.setVisible(false);
            this.dispose();
            subForumPage.setVisible(true);
            CurrentStatus.currFrame = subForumPage;
        }

    }//GEN-LAST:event_jButtonDeleteThreadActionPerformed

    private void getNotificationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNotificationsButtonActionPerformed
        NotificationsPage notificationsPage = new NotificationsPage();
        notificationsPage.setVisible(true);
        this.getNotificationsButton.setBackground(new Color(214, 217, 223));

    }//GEN-LAST:event_getNotificationsButtonActionPerformed

    private void jButtonNotifyMainThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNotifyMainThreadActionPerformed
        askForNotification();
    }//GEN-LAST:event_jButtonNotifyMainThreadActionPerformed

    private void jButtonEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditActionPerformed
        String content = this.jTextFieldAddContent.getText();
        String title = this.jTextFieldAddTitle.getText();
        Member member = (Member) CurrentStatus.currUser;
        currTM.setContent(content);
        currTM.setTitle(title);
        boolean editThread = member.editThread(currTM);
        if (editThread) {
            updateListOfThreads();
            this.jTextFieldAddContent.setText("");
            this.jTextFieldAddTitle.setText("");
            this.jButtonEdit.setVisible(false);
            this.jButtonEditThread.setVisible(true);
            this.jButtonEnterThread.setVisible(false);
        } else {
            this.jLabelError.setVisible(true);
        }
    }//GEN-LAST:event_jButtonEditActionPerformed

    private void jButtonEditThreadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditThreadActionPerformed
        ThreadMessage selectedtm = (ThreadMessage) this.jListThreads.getSelectedValue();
        if (selectedtm == null) {
            return;
        }
        String title = selectedtm.getTitle();
        String content = selectedtm.getContent();
        this.jTextFieldAddTitle.setText(title);
        this.jTextFieldAddContent.setText(content);
        this.jButtonEdit.setVisible(true);
        this.jButtonPublisThread.setVisible(false);
        this.currTM = selectedtm;
    }//GEN-LAST:event_jButtonEditThreadActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton getNotificationsButton;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonDeleteThread;
    private javax.swing.JButton jButtonEdit;
    private javax.swing.JButton jButtonEditThread;
    private javax.swing.JButton jButtonEnterThread;
    private javax.swing.JButton jButtonNotifyMainThread;
    private javax.swing.JButton jButtonPublisThread;
    private javax.swing.JButton jButtonSignout;
    private javax.swing.JLabel jLabelError;
    private javax.swing.JLabel jLabelErrorCannotDelete;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JList jListThreads;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldAddContent;
    private javax.swing.JTextField jTextFieldAddTitle;
    private javax.swing.JButton logInButton;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void askForNotification() {
        if (!(CurrentStatus.currUser instanceof Member)) {
            return;
        }
        Member m = (Member) CurrentStatus.currUser;
        boolean hasNotifications = m.hasNotifications();
        if (hasNotifications) {
            this.getNotificationsButton.setBackground(Color.RED);
        }
    }

    @Override
    public void makeAnEvent() {
        this.jButtonNotifyMainThread.doClick();
    }

    private void updateListOfThreads() {
        DefaultListModel listModel = new DefaultListModel();
        Forum f = CurrentStatus.currForum;
        SubForum sf = CurrentStatus.currSubForum;
        List<ThreadMessage> listOfThreads;
        listOfThreads = CurrentStatus.currUser.viewThreadMessages(f.getForumName(),
                sf.getSubForumName());
        for (ThreadMessage tm : listOfThreads) {
            listModel.addElement(tm);
        }
        jListThreads.setModel(listModel);
    }
}
