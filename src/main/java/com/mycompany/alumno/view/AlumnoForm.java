/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alumno.view;

import com.mycompany.alumno.controller.AlumnoController;
import com.mycompany.alumno.model.Alumno;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * CAPA DE PRESENTACIÓN - Formulario Swing para gestión de alumnos
 */
public class AlumnoForm extends JFrame {
    
    private AlumnoController controller;
    
    // Componentes del formulario
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JTextField txtEdad;
    private JTextField txtCarrera;
    private JTextField txtBuscar;
    private JTable tablaAlumnos;
    private DefaultTableModel modeloTabla;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnBuscar;
    
    private Long idAlumnoSeleccionado = null;
    
    public AlumnoForm() {
        controller = new AlumnoController();
        initComponents();
        cargarAlumnos();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión de Alumnos - 3 Capas con JPA");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // Panel superior con el formulario
        JPanel panelFormulario = crearPanelFormulario();
        add(panelFormulario, BorderLayout.NORTH);
        
        // Panel central con la tabla
        JPanel panelTabla = crearPanelTabla();
        add(panelTabla, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelBotones = crearPanelBotones();
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del Alumno"));
        panel.setBackground(new Color(240, 240, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        // Apellido
        gbc.gridx = 2;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 3;
        txtApellido = new JTextField(20);
        panel.add(txtApellido, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);
        
        // Edad
        gbc.gridx = 2;
        panel.add(new JLabel("Edad:"), gbc);
        gbc.gridx = 3;
        txtEdad = new JTextField(20);
        panel.add(txtEdad, gbc);
        
        // Carrera
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Carrera:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        txtCarrera = new JTextField(20);
        panel.add(txtCarrera, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Alumnos"));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("Buscar:"));
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.addActionListener(e -> buscarAlumnos());
        panelBusqueda.add(btnBuscar);
        
        JButton btnMostrarTodos = new JButton("📋 Mostrar Todos");
        btnMostrarTodos.addActionListener(e -> cargarAlumnos());
        panelBusqueda.add(btnMostrarTodos);
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellido", "Email", "Edad", "Carrera"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAlumnos = new JTable(modeloTabla);
        tablaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAlumnos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarAlumnoSeleccionado();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(new Color(240, 240, 245));
        
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(e -> guardarAlumno());
        
        btnActualizar = new JButton("✏️ Actualizar");
        btnActualizar.setBackground(new Color(33, 150, 243));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> actualizarAlumno());
        
        btnEliminar = new JButton("🗑️ Eliminar");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> eliminarAlumno());
        
        btnLimpiar = new JButton("🧹 Limpiar");
        btnLimpiar.setBackground(new Color(158, 158, 158));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        
        panel.add(btnGuardar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnLimpiar);
        
        return panel;
    }
    
    private void guardarAlumno() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String carrera = txtCarrera.getText().trim();
            
            Integer edad = edadStr.isEmpty() ? null : Integer.parseInt(edadStr);
            
            boolean exito = controller.guardarAlumno(nombre, apellido, email, edad, carrera);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Alumno guardado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAlumnos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al guardar el alumno", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ La edad debe ser un número válido", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarAlumno() {
        if (idAlumnoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Seleccione un alumno de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String email = txtEmail.getText().trim();
            String edadStr = txtEdad.getText().trim();
            String carrera = txtCarrera.getText().trim();
            
            Integer edad = edadStr.isEmpty() ? null : Integer.parseInt(edadStr);
            
            boolean exito = controller.actualizarAlumno(
                idAlumnoSeleccionado, nombre, apellido, email, edad, carrera
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Alumno actualizado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAlumnos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al actualizar el alumno", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Error: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarAlumno() {
        if (idAlumnoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Seleccione un alumno de la tabla", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro de eliminar este alumno?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = controller.eliminarAlumno(idAlumnoSeleccionado);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Alumno eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarAlumnos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Error al eliminar el alumno", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void buscarAlumnos() {
        String busqueda = txtBuscar.getText().trim();
        if (busqueda.isEmpty()) {
            cargarAlumnos();
            return;
        }
        
        List<Alumno> alumnos = controller.buscarAlumnosPorNombre(busqueda);
        actualizarTabla(alumnos);
    }
    
    private void cargarAlumnos() {
        List<Alumno> alumnos = controller.obtenerTodosLosAlumnos();
        actualizarTabla(alumnos);
    }
    
    private void actualizarTabla(List<Alumno> alumnos) {
        modeloTabla.setRowCount(0);
        for (Alumno alumno : alumnos) {
            Object[] fila = {
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getEmail(),
                alumno.getEdad(),
                alumno.getCarrera()
            };
            modeloTabla.addRow(fila);
        }
    }
    
    private void cargarAlumnoSeleccionado() {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            idAlumnoSeleccionado = (Long) modeloTabla.getValueAt(filaSeleccionada, 0);
            txtNombre.setText((String) modeloTabla.getValueAt(filaSeleccionada, 1));
            txtApellido.setText((String) modeloTabla.getValueAt(filaSeleccionada, 2));
            txtEmail.setText((String) modeloTabla.getValueAt(filaSeleccionada, 3));
            
            Object edad = modeloTabla.getValueAt(filaSeleccionada, 4);
            txtEdad.setText(edad != null ? edad.toString() : "");
            
            Object carrera = modeloTabla.getValueAt(filaSeleccionada, 5);
            txtCarrera.setText(carrera != null ? carrera.toString() : "");
        }
    }
    
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtEmail.setText("");
        txtEdad.setText("");
        txtCarrera.setText("");
        txtBuscar.setText("");
        idAlumnoSeleccionado = null;
        tablaAlumnos.clearSelection();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            AlumnoForm form = new AlumnoForm();
            form.setVisible(true);
        });
    }
}