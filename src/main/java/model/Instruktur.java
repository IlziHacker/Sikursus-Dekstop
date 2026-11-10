package model;

<<<<<<< HEAD
public class Instruktur extends Orang {
    private String keahlian;
    private String email;

    public Instruktur() {}

    public Instruktur(int id, String nama, String noHp, String keahlian) {
        this(id, nama, noHp, keahlian, "");
    }

    public Instruktur(int id, String nama, String noHp, String keahlian, String email) {
        super(id, nama, noHp);
        setKeahlian(keahlian);
        setEmail(email);
    }

    public String getKeahlian() { return keahlian; }
    public final void setKeahlian(String keahlian) {
        if (keahlian == null || keahlian.trim().isEmpty()) throw new IllegalArgumentException("Keahlian wajib diisi");
        this.keahlian = keahlian.trim();
    }

    public String getEmail() { return email == null ? "" : email; }
    public final void setEmail(String email) {
        String value = email == null ? "" : email.trim();
        if (!value.isEmpty() && !value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Format email tidak valid");
        }
        this.email = value;
=======

public class Instruktur extends Orang {
    private String keahlian;

    public Instruktur(int id, String nama, String noHp, String keahlian) {
        super(id, nama, noHp);
        setKeahlian(keahlian);
    }

    public String getKeahlian() {
        return keahlian;
    }

    public final void setKeahlian(String keahlian) {
        if (keahlian == null || keahlian.trim().isEmpty()) {
            throw new IllegalArgumentException("Keahlian wajib diisi");
        }
        this.keahlian = keahlian.trim();
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
    }

    @Override
    public String getInfo() {
<<<<<<< HEAD
        return "INSTRUKTUR | " + super.getInfo() + " | Keahlian: " + getKeahlian();
=======
        return "INSTRUKTUR | " + super.getInfo()
                + " | Keahlian: " + getKeahlian();
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
    }
}