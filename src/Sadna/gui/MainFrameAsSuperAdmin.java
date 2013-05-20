/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sadna.gui;

import Sadna.Client.API.ClientCommunicationHandlerInterface;
import Sadna.Client.ClientConnectionHandler;
import Sadna.Client.SuperAdmin;
import Sadna.db.Forum;
import Sadna.db.Policy;
import Sadna.db.PolicyEnums.enumAssignModerator;
import Sadna.db.PolicyEnums.enumCancelModerator;
import Sadna.db.PolicyEnums.enumDelete;
import Sadna.db.PolicyEnums.enumNotiFriends;
import Sadna.db.PolicyEnums.enumNotiImidiOrAgre;
import java.util.Enumeration;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author fistuk
 */
public class MainFrameAsSuperAdmin extends javax.swing.JFrame {

    String host = "192.168.1.104";
    int port = 3333;

    public MainFrameAsSuperAdmin() {
        initComponents();
        this.setResizable(false);
        ClientCommunicationHandlerInterface ch = new ClientConnectionHandler(host, port);
        DefaultListModel listOfForums;
        if (CurrentStatus.currUser == null) {

            CurrentStatus.currUser = new SuperAdmin("superAdmin", "superAdmin1234", "email", ch);
//            CurrentStatus.currUser = new User(ch);
        }
        List<Forum> viewForums = UpdateListOfForums();


        String text = jLabelNumberOfForums.getText();
        jLabelNumberOfForums.setText(text + " " + String.valueOf(viewForums.size()));
        SuperAdmin admin = (SuperAdmin) CurrentStatus.currUser;
        List<String> commonMembers = admin.getCommonMembers();
        listOfForums = new DefaultListModel();
        for (String string : commonMembers) {
            listOfForums.addElement(string);
        }
        commonMembersList.setModel(listOfForums);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deleteMsgGroup = new javax.swing.ButtonGroup();
        removeModeratorGroup = new javax.swing.ButtonGroup();
        addModeratorGroup = new javax.swing.ButtonGroup();
        notificationsContribution = new javax.swing.ButtonGroup();
        notificationsFreq = new javax.swing.ButtonGroup();
        headLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        forumsList = new javax.swing.JList();
        jButtonInitiateForum = new javax.swing.JButton();
        jLabelNumberOfForums = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        commonMembersList = new javax.swing.JList();
        jButtonDeleteForum = new javax.swing.JButton();
        jTextFieldNewAdminUser = new javax.swing.JTextField();
        jTextFieldNewAdminPassword = new javax.swing.JTextField();
        jLabelNewAdminUserName = new javax.swing.JLabel();
        jLabelNewAdminPassword = new javax.swing.JLabel();
        jLabelNewForumName = new javax.swing.JLabel();
        jTextFieldNewForumName = new javax.swing.JTextField();
        jLabelShowCommonUsers = new javax.swing.JLabel();
        jRadioButtonFreqImd = new javax.swing.JRadioButton();
        jRadioButtonFreqAgg = new javax.swing.JRadioButton();
        jRadioButtonContAll = new javax.swing.JRadioButton();
        jRadioButtonContSubForum = new javax.swing.JRadioButton();
        jRadioButtonDeleteNoModerator = new javax.swing.JRadioButton();
        jRadioButtonDeleteYesModerator = new javax.swing.JRadioButton();
        jRadioButtonAddModeratorNoRest = new javax.swing.JRadioButton();
        jRadioButtonAddModeratorNumberOfMsg = new javax.swing.JRadioButton();
        jRadioButtonAddModeratorTime = new javax.swing.JRadioButton();
        jTextFieldNumberOfMsgs = new javax.swing.JTextField();
        jTextNumberOfWeeks = new javax.swing.JTextField();
        jRadioButtonDeleteModeratorNoRest = new javax.swing.JRadioButton();
        jRadioButtonDeleteModeratorRest = new javax.swing.JRadioButton();
        jLabelForumPolicies = new javax.swing.JLabel();
        jLabelNotiFreq = new javax.swing.JLabel();
        jLabelDeletePost = new javax.swing.JLabel();
        jLabelNotiCont = new javax.swing.JLabel();
        jLabelDelModerator = new javax.swing.JLabel();
        jLabelAddModerator2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        headLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        headLabel.setText("Forums management");

        forumsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of forums", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        forumsList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clickHandler(evt);
            }
        });
        jScrollPane1.setViewportView(forumsList);

        jButtonInitiateForum.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jButtonInitiateForum.setText("initiate new forum");
        jButtonInitiateForum.setToolTipText("an option to initiate a new forum");
        jButtonInitiateForum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInitiateForumActionPerformed(evt);
            }
        });

        jLabelNumberOfForums.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelNumberOfForums.setText("Number of forums: ");

        commonMembersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "list of common members" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        commonMembersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commonMembersListclickHandler(evt);
            }
        });
        jScrollPane2.setViewportView(commonMembersList);

        jButtonDeleteForum.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        jButtonDeleteForum.setText("delete forum");
        jButtonDeleteForum.setToolTipText("delete the selected forum and all its content");
        jButtonDeleteForum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteForumActionPerformed(evt);
            }
        });

        jLabelNewAdminUserName.setText("Admin userName");

        jLabelNewAdminPassword.setText("Admin password");

        jLabelNewForumName.setText("new forum name");

        jLabelShowCommonUsers.setText("list of common members");

        notificationsFreq.add(jRadioButtonFreqImd);
        jRadioButtonFreqImd.setText("Imidiate");

        notificationsFreq.add(jRadioButtonFreqAgg);
        jRadioButtonFreqAgg.setText("Aggragate");

        notificationsContribution.add(jRadioButtonContAll);
        jRadioButtonContAll.setText("All members in forum");

        notificationsContribution.add(jRadioButtonContSubForum);
        jRadioButtonContSubForum.setText("All publishers in sub-forum");

        deleteMsgGroup.add(jRadioButtonDeleteNoModerator);
        jRadioButtonDeleteNoModerator.setText("publisher and admin");

        deleteMsgGroup.add(jRadioButtonDeleteYesModerator);
        jRadioButtonDeleteYesModerator.setText("publisher, moderators and admin");

        addModeratorGroup.add(jRadioButtonAddModeratorNoRest);
        jRadioButtonAddModeratorNoRest.setText("No restrictions");

        addModeratorGroup.add(jRadioButtonAddModeratorNumberOfMsg);
        jRadioButtonAddModeratorNumberOfMsg.setText("publis enough messages");

        addModeratorGroup.add(jRadioButtonAddModeratorTime);
        jRadioButtonAddModeratorTime.setText("enough time in forum");

        removeModeratorGroup.add(jRadioButtonDeleteModeratorNoRest);
        jRadioButtonDeleteModeratorNoRest.setText("No restrictions");

        removeModeratorGroup.add(jRadioButtonDeleteModeratorRest);
        jRadioButtonDeleteModeratorRest.setText("Cannot delete last moderator");

        jLabelForumPolicies.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabelForumPolicies.setText("New Forum Policies");

        jLabelNotiFreq.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelNotiFreq.setText("When to notify a member with the notifications?");

        jLabelDeletePost.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelDeletePost.setText("Who can delete a post or a thread?");

        jLabelNotiCont.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelNotiCont.setText("Who will get a notifications in case of a change?");

        jLabelDelModerator.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelDelModerator.setText("How to restrict the deletion of a moderator?");

        jLabelAddModerator2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelAddModerator2.setText("Who can be a moderator?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNewAdminUser, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabelNewForumName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabelNewAdminUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldNewForumName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonContSubForum)
                                    .addComponent(jRadioButtonContAll)
                                    .addComponent(jRadioButtonFreqAgg)
                                    .addComponent(jRadioButtonFreqImd))
                                .addGap(87, 87, 87))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonDeleteYesModerator)
                                    .addComponent(jRadioButtonDeleteNoModerator))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jLabelShowCommonUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(116, 116, 116)
                                        .addComponent(jLabelForumPolicies, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabelNotiFreq, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDeletePost, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonDeleteModeratorNoRest)
                                    .addComponent(jRadioButtonDeleteModeratorRest)
                                    .addComponent(jLabelDelModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelAddModerator2, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(24, 24, 24))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLabelNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jRadioButtonAddModeratorNumberOfMsg)
                                            .addComponent(jRadioButtonAddModeratorTime))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextFieldNumberOfMsgs, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jTextNumberOfWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addComponent(jLabelNotiCont, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jRadioButtonAddModeratorNoRest)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonDeleteForum, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(82, 82, 82))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelForumPolicies, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNotiFreq, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonDeleteForum)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelShowCommonUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabelNotiCont, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(96, 96, 96))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelNewForumName, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldNewForumName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextFieldNewAdminUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jRadioButtonFreqImd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonFreqAgg)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(jLabelNewAdminUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelDeletePost, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButtonDeleteNoModerator)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jRadioButtonDeleteYesModerator)
                                        .addGap(5, 5, 5)
                                        .addComponent(jLabelAddModerator2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(4, 4, 4)
                        .addComponent(jRadioButtonAddModeratorNoRest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButtonAddModeratorNumberOfMsg)
                            .addComponent(jTextFieldNumberOfMsgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButtonAddModeratorTime)
                            .addComponent(jTextNumberOfWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(82, 82, 82))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButtonContAll)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonContSubForum)
                                .addGap(12, 12, 12)
                                .addComponent(jLabelDelModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonDeleteModeratorNoRest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButtonDeleteModeratorRest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void clickHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clickHandler
//        if (!(CurrentStatus.currUser instanceof SuperAdmin)) {
//            return;
//        }
//        int clickCount = evt.getClickCount();
//        if (clickCount == 1) {
//            String text = "";
//            text = "Number of members in forum: ";
//            Forum selectedForum = (Forum) this.forumsList.getSelectedValue();
//            SuperAdmin admin = (SuperAdmin) CurrentStatus.currUser;
//            List<Member> allForumMembers = admin.getAllForumMembers(selectedForum.getForumName());
//            text += allForumMembers.size();
//            this.jLabelNumberOfMembersInForum.setText(text);
//
//
//        }
    }//GEN-LAST:event_clickHandler

    private void commonMembersListclickHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commonMembersListclickHandler
        // TODO add your handling code here:
    }//GEN-LAST:event_commonMembersListclickHandler

    private void jButtonDeleteForumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteForumActionPerformed
        Forum selectedForum = (Forum) this.forumsList.getSelectedValue();
        if (selectedForum == null) {
            return;
        }
        SuperAdmin admin = (SuperAdmin) CurrentStatus.currUser;
        boolean deleteForum = admin.deleteForum(selectedForum.getForumName());
        if (deleteForum) {
            UpdateListOfForums();
            String numberOfForums = this.jLabelNumberOfForums.getText();
            String substring = numberOfForums.substring(0, 19);
            substring += CurrentStatus.currUser.viewForums().size();
            this.jLabelNumberOfForums.setText(substring);
        }

    }//GEN-LAST:event_jButtonDeleteForumActionPerformed

    private void jButtonInitiateForumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInitiateForumActionPerformed
        if (!checkLegalInitForum()) {
            return;
        }
        String userName = this.jTextFieldNewAdminUser.getText();
        String password = this.jTextFieldNewAdminPassword.getText();
        String forumName = this.jTextFieldNewForumName.getText();
        SuperAdmin admin = (SuperAdmin) CurrentStatus.currUser;
        String selectedDeleteMsgText = getSelectedButtonText(deleteMsgGroup);
        String selectedRemoveModText = getSelectedButtonText(removeModeratorGroup);
        String selectedAddModText = getSelectedButtonText(addModeratorGroup);
        String selectedNotiContText = getSelectedButtonText(notificationsContribution);
        String selectedNotiFreqText = getSelectedButtonText(notificationsFreq);
        enumAssignModerator addModEnum = null;
        enumCancelModerator remModeEnum = null;
        enumDelete delPostEnum = null;
        enumNotiFriends NotiContEnum = null;
        enumNotiImidiOrAgre NotiFreqEnum = null;
        int numberOfMsgs = 0;
        int timeOfSeniority = 0;

        switch (selectedDeleteMsgText) {
            case "publisher and admin":
                delPostEnum = enumDelete.LIMITED;
                break;
            case "publisher, moderators and admin":
                delPostEnum = enumDelete.EXTENDED;
                break;
        }
        switch (selectedNotiFreqText) {
            case "Imidiate":
                NotiFreqEnum = enumNotiImidiOrAgre.IMIDIATE;
                break;
            case "Aggragate":
                NotiFreqEnum = enumNotiImidiOrAgre.AGGREGATE;
                break;
        }
        switch (selectedAddModText) {
            case "No restrictions":
                addModEnum = enumAssignModerator.NO_RESTRICTION;
                break;
            case "publis enough messages":
                addModEnum = enumAssignModerator.MIN_PUBLISH;
                String textNumberOfMsgs = this.jTextFieldNumberOfMsgs.getText();
                numberOfMsgs = Integer.parseInt(textNumberOfMsgs);
                break;
            case "enough time in forum":
                addModEnum = enumAssignModerator.SENIORITY;
                String textSeniority = this.jTextNumberOfWeeks.getText();
                timeOfSeniority = Integer.parseInt(textSeniority);
                break;
        }
        switch (selectedNotiContText) {
            case "All members in forum":
                NotiContEnum = enumNotiFriends.ALLMEMBERS;
                break;
            case "All publishers in sub-forum":
                NotiContEnum = enumNotiFriends.PUBLISHERS;
                break;
        }
        switch (selectedRemoveModText) {
            case "No restrictions":
                remModeEnum = enumCancelModerator.NO_RESTRICTION;
                break;
            case "Cannot delete last moderator":
                remModeEnum = enumCancelModerator.RESTRICTED;
                break;
        }
        Policy policy = new Policy(NotiFreqEnum, NotiContEnum, delPostEnum, addModEnum,
                remModeEnum, timeOfSeniority, numberOfMsgs);
        admin.initiateForum(forumName, userName, password, policy);
        this.jTextFieldNewAdminUser.setText("");
        this.jTextFieldNewAdminPassword.setText("");
        this.jTextFieldNewForumName.setText("");
        this.jTextNumberOfWeeks.setText("");
        this.jTextFieldNumberOfMsgs.setText("");
        UpdateListOfForums();
        String numberOfForums = this.jLabelNumberOfForums.getText();
        String substring = numberOfForums.substring(0, 19);
        substring += CurrentStatus.currUser.viewForums().size();
        this.jLabelNumberOfForums.setText(substring);
    }//GEN-LAST:event_jButtonInitiateForumActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup addModeratorGroup;
    private javax.swing.JList commonMembersList;
    private javax.swing.ButtonGroup deleteMsgGroup;
    private javax.swing.JList forumsList;
    private javax.swing.JLabel headLabel;
    private javax.swing.JButton jButtonDeleteForum;
    private javax.swing.JButton jButtonInitiateForum;
    private javax.swing.JLabel jLabelAddModerator2;
    private javax.swing.JLabel jLabelDelModerator;
    private javax.swing.JLabel jLabelDeletePost;
    private javax.swing.JLabel jLabelForumPolicies;
    private javax.swing.JLabel jLabelNewAdminPassword;
    private javax.swing.JLabel jLabelNewAdminUserName;
    private javax.swing.JLabel jLabelNewForumName;
    private javax.swing.JLabel jLabelNotiCont;
    private javax.swing.JLabel jLabelNotiFreq;
    private javax.swing.JLabel jLabelNumberOfForums;
    private javax.swing.JLabel jLabelShowCommonUsers;
    private javax.swing.JRadioButton jRadioButtonAddModeratorNoRest;
    private javax.swing.JRadioButton jRadioButtonAddModeratorNumberOfMsg;
    private javax.swing.JRadioButton jRadioButtonAddModeratorTime;
    private javax.swing.JRadioButton jRadioButtonContAll;
    private javax.swing.JRadioButton jRadioButtonContSubForum;
    private javax.swing.JRadioButton jRadioButtonDeleteModeratorNoRest;
    private javax.swing.JRadioButton jRadioButtonDeleteModeratorRest;
    private javax.swing.JRadioButton jRadioButtonDeleteNoModerator;
    private javax.swing.JRadioButton jRadioButtonDeleteYesModerator;
    private javax.swing.JRadioButton jRadioButtonFreqAgg;
    private javax.swing.JRadioButton jRadioButtonFreqImd;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldNewAdminPassword;
    private javax.swing.JTextField jTextFieldNewAdminUser;
    private javax.swing.JTextField jTextFieldNewForumName;
    private javax.swing.JTextField jTextFieldNumberOfMsgs;
    private javax.swing.JTextField jTextNumberOfWeeks;
    private javax.swing.ButtonGroup notificationsContribution;
    private javax.swing.ButtonGroup notificationsFreq;
    private javax.swing.ButtonGroup removeModeratorGroup;
    // End of variables declaration//GEN-END:variables

    private List<Forum> UpdateListOfForums() {
        List<Forum> viewForums = CurrentStatus.currUser.viewForums();
        DefaultListModel listOfForums = new DefaultListModel();
        for (Forum f : viewForums) {
            listOfForums.addElement(f);
        }
        forumsList.setModel(listOfForums);
        return viewForums;
    }

    private boolean checkLegalInitForum() {
        if (this.jTextFieldNewForumName.getText().isEmpty()) {
            return false;
        }
        if (this.jTextFieldNewAdminUser.getText().isEmpty()) {
            return false;
        }
        if (this.jTextFieldNewAdminPassword.getText().isEmpty()) {
            return false;
        }

        String selectedDeleteMsgText = getSelectedButtonText(deleteMsgGroup);
        String selectedRemoveModText = getSelectedButtonText(removeModeratorGroup);
        String selectedAddModText = getSelectedButtonText(addModeratorGroup);
        String selectedNotiContText = getSelectedButtonText(notificationsContribution);
        String selectedNotiFreqText = getSelectedButtonText(notificationsFreq);

        if (selectedDeleteMsgText == null || selectedRemoveModText == null || selectedAddModText == null
                || selectedNotiContText == null || selectedNotiFreqText == null) {
            return false;
        }
        
        if (selectedAddModText.equals("publis enough messages") &&
                this.jTextFieldNumberOfMsgs.getText().isEmpty()){
            return false;
        }
        if (selectedAddModText.equals("enough time in forum") &&
                this.jTextNumberOfWeeks.getText().isEmpty()){
            return false;
        }
        return true;
    }

    private String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrameAsSuperAdmin().setVisible(true);
            }
        });
    }
}
