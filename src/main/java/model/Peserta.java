package model;

<<<<<<< HEAD
public class Peserta extends Orang {
    private String nim;
    private String jenisKelamin;
    private String prodi;
    private String email;

    public Peserta() {}

    public Peserta(int id, String nama, String jenisKelamin, String prodi,
                   String noHp, String email, String nim) {
        super(id, nama, noHp);
        setNim(nim);
        setJenisKelamin(jenisKelamin);
        setProdi(prodi);
        setEmail(email);
    }

    // Konstruktor kompatibilitas untuk praktikum P5/P6 lama.
    public Peserta(int id, String nama, String noHp, String nim, String prodi) {
        this(id, nama, "L", prodi, noHp,
                (nim == null ? "" : nim.trim()) + "@example.test", nim);
    }

    public String getNim() { return nim; }
    public final void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty()) throw new IllegalArgumentException("NIM wajib diisi");
        this.nim = nim.trim();
    }

    public String getJenisKelamin() { return jenisKelamin; }
    public final void setJenisKelamin(String jenisKelamin) {
        String value = jenisKelamin == null ? "" : jenisKelamin.trim().toUpperCase();
        if (!"L".equals(value) && !"P".equals(value)) {
            throw new IllegalArgumentException("Jenis kelamin harus L atau P");
        }
        this.jenisKelamin = value;
=======
// Tetap pertahankan warisan dari class Orang agar class lain tidak error
public class Peserta extends Orang {
    private String nim;
    private String prodi;
    private String jenisKelamin; // Tambahan baru
    private String email;        // Tambahan baru

    // CONSTRUCTOR 1: Untuk mempertahankan kode lama Anda agar tidak error
    public Peserta(int id, String nama, String noHp, String nim, String prodi) {
        super(id, nama, noHp);
        setNim(nim);
        setProdi(prodi);
    }

    // CONSTRUCTOR 2: Untuk kebutuhan fitur database baru (XAMPP)
    public Peserta(int id, String nama, String noHp, String nim, String jenisKelamin, String prodi, String email) {
        super(id, nama, noHp);
        setNim(nim);
        setProdi(prodi);
        this.jenisKelamin = jenisKelamin;
        this.email = email;
    }

    // Getter & Setter untuk NIM dan Prodi (dengan Validasi lama Anda)
    public String getNim() { return nim; }
    public final void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM wajib diisi");
        }
        this.nim = nim.trim();
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
    }

    public String getProdi() { return prodi; }
    public final void setProdi(String prodi) {
<<<<<<< HEAD
        if (prodi == null || prodi.trim().isEmpty()) throw new IllegalArgumentException("Prodi wajib diisi");
        this.prodi = prodi.trim();
    }

    public String getEmail() { return email == null ? "" : email; }
    public final void setEmail(String email) {
        String value = email == null ? "" : email.trim();
        if (!value.isEmpty() && !value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Format email tidak valid");
        }
        this.email = value;
    }

    @Override
    public String getInfo() {
        return "PESERTA | " + super.getInfo() + " | NIM: " + getNim()
                + " | JK: " + getJenisKelamin() + " | Prodi: " + getProdi();
=======
        if (prodi == null || prodi.trim().isEmpty()) {
            throw new IllegalArgumentException("Prodi wajib diisi");
        }
        this.prodi = prodi.trim();
    }

    // Getter & Setter Tambahan Baru
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String getInfo() {
        return "PESERTA | " + super.getInfo()
                + " | NIM: " + getNim()
                + " | Prodi: " + getProdi();
>>>>>>> 6bd52ef41db6b42a8147c8d43d7f520e89ae3d0d
    }
}