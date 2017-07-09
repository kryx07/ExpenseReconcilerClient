package kryx07.expensereconcilerclient.db;

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

import kryx07.expensereconcilerclient.model.users.Users;

public class Converters {
    @TypeConverter
    public static LocalDate fromTimestamp(String value) {
        return value == null ? null : LocalDate.parse(value);
    }

    @TypeConverter
    public static String dateToTimestamp(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static String bigDecimalToString(BigDecimal amount) {
        return amount == null ? null : amount.toString();
    }

    @TypeConverter
    public static BigDecimal stringToBigDecimal(String amount) {
        return amount == null ? null : new BigDecimal(amount);
    }
/*
    @TypeConverter
    public static String usersToString(Users users) {
        Log.e("Converter: ", users.toString());
        return users  == null ? null : new Gson().toJson(users, Users.class);
    }

    @TypeConverter
    public static Users stringToUsers(String string) {
        Log.e("Converter: ", string);
        return string == null ? null : new Gson().fromJson(string, Users.class);
    }*/
}
