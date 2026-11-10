<<<<<<< HEAD

package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Peserta;
import util.LookupItem;

public class PesertaDAO {

    private Peserta map(ResultSet rs) throws SQLException {
        Peserta p = new Peserta();

        p.setId(rs.getInt("id_peserta"));
        p.setNim(rs.getString("nim"));
        p.setNama(rs.getString("nama"));
        p.setJenisKelamin(rs.getString("jenis_kelamin"));
        p.setProdi(rs.getString("prodi"));
        p.setNoHp(rs.getString("no_hp"));
        p.setEmail(rs.getString("email"));

        return p;
    }

    public boolean insert(Peserta p) throws SQLException {
        String sql = "INSERT INTO peserta"
                + "(nim, nama, jenis_kelamin, prodi, no_hp, email) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            bind(ps, p);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean update(Peserta p) throws SQLException {
        String sql = "UPDATE peserta SET "
                + "nim = ?, nama = ?, jenis_kelamin = ?, "
                + "prodi = ?, no_hp = ?, email = ? "
                + "WHERE id_peserta = ?";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            bind(ps, p);
            ps.setInt(7, p.getId());

            return ps.executeUpdate() > 0;
        }
    }

    private void bind(PreparedStatement ps, Peserta p)
            throws SQLException {

        ps.setString(1, p.getNim());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getJenisKelamin());
        ps.setString(4, p.getProdi());
        ps.setString(5, p.getNoHp());
        ps.setString(6, p.getEmail());
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM peserta WHERE id_peserta = ?";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Peserta> findAll() throws SQLException {
        return search("");
    }

    public List<Peserta> search(String q) throws SQLException {
        List<Peserta> out = new ArrayList<>();

        String sql = "SELECT id_peserta, nim, nama, jenis_kelamin, "
                + "prodi, no_hp, email FROM peserta "
                + "WHERE nim LIKE ? OR nama LIKE ? OR prodi LIKE ? "
                + "ORDER BY id_peserta DESC";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            String x = "%" + (q == null ? "" : q.trim()) + "%";

            ps.setString(1, x);
            ps.setString(2, x);
            ps.setString(3, x);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    out.add(map(rs));
                }
            }
        }

        return out;
    }

    public List<LookupItem> lookup() throws SQLException {
        List<LookupItem> out = new ArrayList<>();

        String sql = "SELECT id_peserta, nim, nama "
                + "FROM peserta ORDER BY nama";

        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(new LookupItem(
                        rs.getInt(1),
                        rs.getString(2) + " - " + rs.getString(3)
                ));
            }
        }

        return out;
    }
}
=======
package dao;
import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Peserta;


public class PesertaDAO {
public boolean insert(Peserta p) throws SQLException {
String sql = "INSERT INTO peserta "
+ "(nim, nama, jenis_kelamin, prodi, no_hp, email) "
+ "VALUES (?, ?, ?, ?, ?, ?)";
try (Connection conn = Database.getConnection();
PreparedStatement ps = conn.prepareStatement(sql)) {
ps.setString(1, p.getNim());
ps.setString(2, p.getNama());
ps.setString(3, p.getJenisKelamin());
ps.setString(4, p.getProdi());
ps.setString(5, p.getNoHp());
ps.setString(6, p.getEmail());
return ps.executeUpdate() == 1;
}
}
}
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
