package edu.scu.detrasher;

/**
 * Created by Srivatsa on 21-05-2017.
 */

public class Location {
    /* Private attributes */
    int _location_id;
    String _location_name;
    int _location_floor;
    int _location_trash_id;
    int _location_trash_level;

    /* Constructors */
    public Location()
    {
        /* Default constructor */
    }
    public Location(String location_name, int location_floor, int location_trash_id, int location_trash_level)
    {
        this._location_name = location_name;
        this._location_floor = location_floor;
        this._location_trash_id = location_trash_id;
        this._location_trash_level = location_trash_level;
    }
    /* Get and set Method */
    public int get_location_id()
    {
        return this._location_id;
    }
    public String get_location_name()
    {
        return this._location_name;
    }
    public int get_location_floor()
    {
        return this._location_floor;
    }
    public int get_location_trash_id()
    {
        return this._location_trash_id;
    }
    public int get_location_trash_level() {
        return this._location_trash_level;
    }
    /* Set Methods */
    public void set_location_id(int value)
    {
        this._location_id = value;
    }
    public void set_location_name(String value)
    {
        this._location_name = value;
    }
    public void set_location_floor(int value)
    {
        this._location_floor = value;
    }
    public void set_location_trash_id(int value)
    {
        this._location_trash_id = value;
    }
    public void set_location_trash_level(int value)
    {
        this._location_trash_level = value;
    }
}
