/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.koneksi;
import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Acer
 */
public class modelPengeluaran {

    private String idPengeluaran, tanggal, keterangan, idPengguna;
    private Integer nominal;

    public modelPengeluaran() {
    }

    public modelPengeluaran(String idPengeluaran, Integer nominal, String tanggal, String keterangan, String idPengguna) {
        this.idPengeluaran = idPengeluaran;
        this.nominal = nominal;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.idPengguna = idPengguna;
    }

    public void setIdPengeluaran() {
        this.idPengeluaran = UUID.randomUUID().toString();
    }
    
    public void setIdPengeluaran(String idPengeluaran) {
        this.idPengeluaran = idPengeluaran;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setIdPengguna(String idPengguna) {
        this.idPengguna = idPengguna;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public String getIdPengeluaran() {
        return idPengeluaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public String getIdPengguna() {
        return idPengguna;
    }

    public Integer getNominal() {
        return nominal;
    }
    
    public boolean tambah() {
        setIdPengeluaran();
        String sql = "INSERT INTO pengeluaran "
                + "(id_pengeluaran, nominal, tanggal, keterangan, id_pengguna) "
                + "VALUES (?,?,?,?,?)";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPengeluaran);
            ps.setInt(2, nominal);
            ps.setString(3, tanggal);
            ps.setString(4, keterangan);
            ps.setString(5, idPengguna);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean ubah() {
        String sql = "UPDATE pengeluaran SET "
                + "nominal = ?, tanggal = ?, keterangan = ?, id_pengguna = ? "
                + "WHERE id_pengeluaran = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setInt(1, nominal);
            ps.setString(2, tanggal);
            ps.setString(3, keterangan);
            ps.setString(4, idPengguna);
            ps.setString(5, idPengeluaran);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean hapus() {
        String sql = "DELETE FROM pengeluaran WHERE id_pengeluaran = ?";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, idPengeluaran);
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<modelPengeluaran>tampilSemua() {
          List<modelPengeluaran> daftar = new ArrayList<>();
          
          String sql = "SELECT k.id_pengeluaran, k.nominal, k.tanggal, k.keterangan, g.nama_pengguna FROM pengeluaran k JOIN pengguna g "
                  +"ON k.id_Pengguna = g.id_pengguna  "
                  + "ORDER BY k.tanggal DESC";
        
        try{
            Connection conn = koneksi.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                modelPengeluaran pengeluaran = new modelPengeluaran(
                        rs.getString("id_pengeluaran"),
                        rs.getInt("nominal"),
                        rs.getString("tanggal"),
                        rs.getString("keterangan"),
                        rs.getString("nama_pengguna")
                );
                
                daftar.add(pengeluaran);
            }
            
        } catch (SQLException e) {
            System.out.println("Gagal menampilkan pengeluaran: " + e.getMessage());
        }
        return daftar;
    }

}
