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
import Sadna.db.PolicyEnums.*;
import dbTABLES.Forumdb;

import javax.swing.*;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author fistuk
 */
public class MainFrameAsSuperAdmin extends ForumJFrame {

    String host = "192.168.1.108";
    int port = 3333;

    public MainFrameAsSuperAdmin() {
        initComponents();
        this.setResizable(false);
//        ClientCommunicationHandlerInterface ch = new ClientConnectionHandler(host, port);
        DefaultListModel listOfForums;
//        if (CurrentStatus.currUser == null) {
//
//            CurrentStatus.currUser = new SuperAdmin("superAdmin", "superAdmin1234", "email", ch);
////            CurrentStatus.currUser = new User(ch);
//        }
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
        verifyNewRegisterd = new javax.swing.ButtonGroup();
        moderatorAbilities = new javax.swing.ButtonGroup();
        machineLearning = new javax.swing.ButtonGroup();
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
        jLabelMailPolicy = new javax.swing.JLabel();
        jRadioButtonMailSend = new javax.swing.JRadioButton();
        jRadioButtonMailDuplicate = new javax.swing.JRadioButton();
        jLabelModeratorAbilities = new javax.swing.JLabel();
        jRadioButtonModeratorAbilitiesYes = new javax.swing.JRadioButton();
        jRadioButtonModeratorAbilitiesNo = new javax.swing.JRadioButton();
        jLabelMachineLearning = new javax.swing.JLabel();
        jRadioButtonMachineLearningYes = new javax.swing.JRadioButton();
        jRadioButtonMachineLearningNo = new javax.swing.JRadioButton();
        jLabelListOfForbiddenWords = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextAreaListOfForbiddenWords = new javax.swing.JTextArea();

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

        jLabelMailPolicy.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelMailPolicy.setText("How to verify new registered members?");

        verifyNewRegisterd.add(jRadioButtonMailSend);
        jRadioButtonMailSend.setText("send verification mail");

        verifyNewRegisterd.add(jRadioButtonMailDuplicate);
        jRadioButtonMailDuplicate.setText("check that there is no duplicate mails");
        jRadioButtonMailDuplicate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMailDuplicateActionPerformed(evt);
            }
        });

        jLabelModeratorAbilities.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelModeratorAbilities.setText("Does the moderators can delete and edit posts?");

        moderatorAbilities.add(jRadioButtonModeratorAbilitiesYes);
        jRadioButtonModeratorAbilitiesYes.setText("Yes");

        moderatorAbilities.add(jRadioButtonModeratorAbilitiesNo);
        jRadioButtonModeratorAbilitiesNo.setText("No");

        jLabelMachineLearning.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabelMachineLearning.setText("Allow machine learning to filtering posts");

        machineLearning.add(jRadioButtonMachineLearningYes);
        jRadioButtonMachineLearningYes.setText("Yes");

        machineLearning.add(jRadioButtonMachineLearningNo);
        jRadioButtonMachineLearningNo.setText("No");

        jLabelListOfForbiddenWords.setText("list of forbidden words");

        jTextAreaListOfForbiddenWords.setColumns(20);
        jTextAreaListOfForbiddenWords.setRows(5);
        jScrollPane4.setViewportView(jTextAreaListOfForbiddenWords);

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
                            .addComponent(jTextFieldNewForumName, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jRadioButtonContSubForum)
                                .addComponent(jRadioButtonContAll)
                                .addComponent(jRadioButtonFreqAgg)
                                .addComponent(jRadioButtonFreqImd)
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
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(20, 20, 20)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButtonMachineLearningYes)
                                                        .addComponent(jLabelMachineLearning, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jRadioButtonMachineLearningNo)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(16, 16, 16)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jRadioButtonMailDuplicate)
                                                        .addComponent(jRadioButtonMailSend))))
                                            .addGap(56, 56, 56))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabelMailPolicy, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(4, 4, 4)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonDeleteNoModerator)
                                    .addComponent(jRadioButtonDeleteYesModerator))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonDeleteForum, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(128, 128, 128))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabelNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonAddModeratorNoRest)
                                    .addComponent(jRadioButtonAddModeratorNumberOfMsg)
                                    .addComponent(jRadioButtonAddModeratorTime))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextFieldNumberOfMsgs, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jTextNumberOfWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelNotiCont, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonModeratorAbilitiesYes)
                                    .addComponent(jLabelModeratorAbilities, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonModeratorAbilitiesNo))))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelListOfForbiddenWords, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelShowCommonUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(68, 68, 68))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(headLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelForumPolicies, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                            .addComponent(jLabelNumberOfForums, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelNotiFreq, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelListOfForbiddenWords))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonInitiateForum, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(85, 85, 85))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabelAddModerator2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabelMailPolicy, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextFieldNumberOfMsgs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextNumberOfWeeks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jRadioButtonAddModeratorNoRest)
                                            .addComponent(jRadioButtonMailSend, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButtonAddModeratorNumberOfMsg)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jRadioButtonAddModeratorTime))
                                            .addComponent(jRadioButtonMailDuplicate, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelNotiCont, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelModeratorAbilities, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButtonContAll)
                                    .addComponent(jRadioButtonModeratorAbilitiesYes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButtonContSubForum)
                                    .addComponent(jRadioButtonModeratorAbilitiesNo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelDelModerator, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelMachineLearning, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButtonDeleteModeratorNoRest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonMachineLearningYes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButtonDeleteModeratorRest, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jRadioButtonMachineLearningNo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(99, 99, 99))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane4)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonDeleteForum)
                                        .addGap(28, 28, 28)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(jLabelNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(35, 35, 35)
                                        .addComponent(jLabelShowCommonUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldNewAdminPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(127, 127, 127))))))
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
        String selectedMachineLearning = getSelectedButtonText(machineLearning);
        String selectedModeratorAbilities = getSelectedButtonText(moderatorAbilities);
        String selectedVerifyNewRegistered = getSelectedButtonText(verifyNewRegisterd);
        enumAssignModerator addModEnum = null;
        enumCancelModerator remModeEnum = null;
        enumDelete delPostEnum = null;
        enumNotiFriends NotiContEnum = null;
        enumNotiImidiOrAgre NotiFreqEnum = null;
        enumSecurity verifyNewRegEnum = null;
        enumModeratorPermissions moderatorAbilitiesEnum = null;
        enumMessageContent machineLearningEnum = null;
        int numberOfMsgs = Integer.parseInt(this.jTextFieldNumberOfMsgs.getText());
        int timeOfSeniority = Integer.parseInt(this.jTextNumberOfWeeks.getText());
        switch (selectedMachineLearning){
            case "Yes":
                machineLearningEnum = enumMessageContent.FILTERED;
                break;
            case "No":
                machineLearningEnum = enumMessageContent.NOT_FILTERED;
                break;
        }
        switch (selectedModeratorAbilities){
            case "Yes":
                moderatorAbilitiesEnum = enumModeratorPermissions.EXTENDED;
                break;
            case "No":
                moderatorAbilitiesEnum = enumModeratorPermissions.LIMITED;
                break;
        }
        switch (selectedVerifyNewRegistered){
            case "send verification mail":
                verifyNewRegEnum = enumSecurity.VERIFY_EMAIL;
                break;
            case "check that there is no duplicate mails":
                verifyNewRegEnum = enumSecurity.NOT_USED_EMAIL;
                break;
        }
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
                                remModeEnum, machineLearningEnum, moderatorAbilitiesEnum,
                                verifyNewRegEnum, timeOfSeniority, numberOfMsgs);
        String forbiddenWords = this.jTextAreaListOfForbiddenWords.getText();
        if (forbiddenWords.equals("")){
            forbiddenWords = "*";
        }
        admin.initiateForum(forumName, userName, password, policy, forbiddenWords); //add new button with forbidden words
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

    private void jRadioButtonMailDuplicateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMailDuplicateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMailDuplicateActionPerformed

    private void commonMembersListclickHandler(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commonMembersListclickHandler
        // TODO add your handling code here:
    }//GEN-LAST:event_commonMembersListclickHandler

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
    private javax.swing.JLabel jLabelListOfForbiddenWords;
    private javax.swing.JLabel jLabelMachineLearning;
    private javax.swing.JLabel jLabelMailPolicy;
    private javax.swing.JLabel jLabelModeratorAbilities;
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
    private javax.swing.JRadioButton jRadioButtonMachineLearningNo;
    private javax.swing.JRadioButton jRadioButtonMachineLearningYes;
    private javax.swing.JRadioButton jRadioButtonMailDuplicate;
    private javax.swing.JRadioButton jRadioButtonMailSend;
    private javax.swing.JRadioButton jRadioButtonModeratorAbilitiesNo;
    private javax.swing.JRadioButton jRadioButtonModeratorAbilitiesYes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextAreaListOfForbiddenWords;
    private javax.swing.JTextField jTextFieldNewAdminPassword;
    private javax.swing.JTextField jTextFieldNewAdminUser;
    private javax.swing.JTextField jTextFieldNewForumName;
    private javax.swing.JTextField jTextFieldNumberOfMsgs;
    private javax.swing.JTextField jTextNumberOfWeeks;
    private javax.swing.ButtonGroup machineLearning;
    private javax.swing.ButtonGroup moderatorAbilities;
    private javax.swing.ButtonGroup notificationsContribution;
    private javax.swing.ButtonGroup notificationsFreq;
    private javax.swing.ButtonGroup removeModeratorGroup;
    private javax.swing.ButtonGroup verifyNewRegisterd;
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

        if (selectedAddModText.equals("publis enough messages")
                && this.jTextFieldNumberOfMsgs.getText().isEmpty()) {
            return false;
        }
        if (selectedAddModText.equals("enough time in forum")
                && this.jTextNumberOfWeeks.getText().isEmpty()) {
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

}
