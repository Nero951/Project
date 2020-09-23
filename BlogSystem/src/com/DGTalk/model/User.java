package com.DGTalk.model;
import com.DGTalk.util.DBUtil;

import java.sql.*;
/**
 * created by Intellij IDEA
 * Description:User
 * User:Wade Winston Wilson
 * Year:2020
 * Time:19:43
 */
public class User {
    public int id;
    public String username;

    public static User register(String username, String password) throws SQLException {
        try (Connection c = DBUtil.getConnection()) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                s.setString(1, username);
                s.setString(2, password);

                s.executeUpdate();

                try (ResultSet r = s.getGeneratedKeys()) {
                    if (!r.next()) {
                        return null;
                    }

                    User user = new User();
                    user.id = r.getInt(1);
                    user.username = username;
                    return user;
                }
            }
        }
    }
}
