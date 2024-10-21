package com.libreria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LibreriaApp extends JFrame {
    private JTable table;
    private JTextField txtTitulo, txtAutor, txtAnio;
    private JButton btnCrear, btnLeer, btnActualizar, btnEliminar;
    private DefaultTableModel tableModel;
    private Connection connection;

    public LibreriaApp() {
        // Configuración de la ventana principal
        setTitle("Gestión de Libros - CRUD");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2));

        // Campos de texto para título, autor y año de publicación
        panelFormulario.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Año de Publicación:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4));

        btnCrear = new JButton("Crear");
        btnLeer = new JButton("Leer");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnCrear);
        panelBotones.add(btnLeer);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Tabla para mostrar los registros
        tableModel = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "Año"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Añadir los componentes a la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Conectar a la base de datos
        try {
            connection = ConexionBD.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Acción para cada botón
        btnCrear.addActionListener(e -> crearLibro());
        btnLeer.addActionListener(e -> leerLibros());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
    }

    // Método para crear un nuevo libro
    private void crearLibro() {
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros");
            
            rs.moveToInsertRow();
            rs.updateString("titulo", txtTitulo.getText());
            rs.updateString("autor", txtAutor.getText());
            rs.updateInt("anio_publicacion", Integer.parseInt(txtAnio.getText()));
            rs.insertRow();

            JOptionPane.showMessageDialog(this, "Libro creado con éxito!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para leer los libros
    private void leerLibros() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM libros");

            // Limpiar la tabla
            tableModel.setRowCount(0);

            // Agregar los datos a la tabla
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("anio_publicacion")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar un libro seleccionado
    private void actualizarLibro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) table.getValueAt(selectedRow, 0);
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                int anio = Integer.parseInt(txtAnio.getText());

                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + id);

                if (rs.next()) {
                    rs.updateString("titulo", titulo);
                    rs.updateString("autor", autor);
                    rs.updateInt("anio_publicacion", anio);
                    rs.updateRow();
                    JOptionPane.showMessageDialog(this, "Libro actualizado con éxito!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar un libro seleccionado
    private void eliminarLibro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) table.getValueAt(selectedRow, 0);
                Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = stmt.executeQuery("SELECT * FROM libros WHERE id = " + id);

                if (rs.next()) {
                    rs.deleteRow();
                    JOptionPane.showMessageDialog(this, "Libro eliminado con éxito!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibreriaApp app = new LibreriaApp();
            app.setVisible(true);
        });
    }
}
