package edu.scu.detrasher;

/**
 * Created by Srivatsa on 21-05-2017.
 */

public class Task {
    /* Private attributes */
    int _task_id;
    int _task_location_id;
    String _task_location_descr;
    int _task_user_id;
    String _task_staff_name;
    int _task_completion_status;
    /* Constructors */
    public Task()
    {
        /* Default constructor */
    }
    public Task(int task_id, int task_location_id, int task_user_id, int task_completion_status)
    {
        this._task_id = task_id;
        this._task_location_id = task_location_id;
        this._task_user_id = task_user_id;
        this._task_completion_status = task_completion_status;
    }
    /* Get and set Method */
    public int get_task_id()
    {
        return this._task_id;
    }
    public int get_task_location_id()
    {
        return this._task_location_id;
    }
    public String get_task_location_descr(){return this._task_location_descr; }
    public int get_task_user_id()
    {
        return this._task_user_id;
    }
    public String get_task_staff_name(){return this._task_staff_name; }
    public int get_task_completion_status()
    {
        return this._task_completion_status;
    }
    /* Set Methods */
    public void set_task_id(int value)
    {
        this._task_id = value;
    }
    public void set_task_location_id(int value)
    {
        this._task_location_id = value;
    }
    public void set_task_location_desc(String value)
    {
        this._task_location_descr = value;
    }
    public void set_task_user_id(int value)
    {
        this._task_user_id = value;
    }
    public void set_task_staff_name(String value)
    {
        this._task_staff_name = value;
    }
    public void set_task_completion_status(int value)
    {
        this._task_completion_status = value;
    }
}
