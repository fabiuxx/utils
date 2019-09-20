/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fa.gs.utils.criteria.sql.build;

/**
 *
 * @author Fabio A. González Sosa
 */
public class Builders {

    public static ProjectionBuilder projection() {
        return new ProjectionBuilder();
    }

    public static JoinBuilder join() {
        return new JoinBuilder();
    }

    public static ConditionBuilder condition() {
        return new ConditionBuilder();
    }

    public static SortingBuilder sorting() {
        return new SortingBuilder();
    }

}
