package edu.scu.detrasher;

/**
 * Created by Srivatsa on 21-05-2017.
 */

public class Location {
    /* Private attributes */
    String _location_name;
    int _location_floor;
    int _location_trash_id;

    /* Constructors */
    public Location()
    {
        /* Default constructor */
    }
    public Location(String location_name, int location_floor, int location_trash_id)
    {
        this._location_name = location_name;
        this._location_floor = location_floor;
        this._location_trash_id = location_trash_id;
    }
    /* Get and set Method */
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
}
