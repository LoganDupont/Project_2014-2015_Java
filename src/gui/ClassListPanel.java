/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.ClassGroup;
import domain.ClassListController;
import domain.Grade;
import domain.SchoolYear;
import domain.Student;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * FXML Controller class
 *
 * @author SAMUEL
 */
public class ClassListPanel extends TreeView {

    @FXML
    TreeView classTreeView;

    ClassListController controller;

    public ClassListPanel() {
        //Controllers
        controller = new ClassListController();

        TreeItem<String> rootItem = new TreeItem<>("Klassen Lijst");
        rootItem.setExpanded(true);
        //data toevoegen in Tree
        for (Grade g : controller.giveAllGrades()) {
            TreeItem<String> grade = new TreeItem<>(g.getGradeString());
            rootItem.getChildren().add(grade);
            for (SchoolYear sy : controller.giveSchoolYearsOfGrade(g)) {
                TreeItem<String> schoolYear = new TreeItem<>(sy.getSchoolYearString());
                grade.getChildren().add(schoolYear);
                for (ClassGroup cg : controller.giveClassGroupOfSchoolYear(sy)) {
                    TreeItem<String> classGroup = new TreeItem<>(cg.getGroupName());
                    schoolYear.getChildren().add(classGroup);
                    for (Student s : controller.giveStudentsOfClassGroup(cg)) {
                        TreeItem<String> student = new TreeItem<>(s.getFullName());
                        classGroup.getChildren().add(student);
                    }
                }
            }
            //TreeView opvullen
            classTreeView = new TreeView<>(rootItem);

        }

    }
}
