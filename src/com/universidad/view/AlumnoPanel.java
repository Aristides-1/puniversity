package com.universidad.view;

import com.universidad.model.Alumno;
import com.universidad.service.UniversidadService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel para gestionar alumnos
 */
public class AlumnoPanel extends JPanel {
    
    private UniversidadService service;
    
    // Componentes
    private JTable tablaAlumnos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnNuevo, btnEditar, btnEliminar, btnVer;
    
    // Colores
    private final Color COLOR_PRIMARY = new Color(13, 110, 253);
    
    public AlumnoPanel(UniversidadService service) {
        this.service = service;
        initComponents();
        actualizarTabla();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con título y botones
        add(crearPanelSuperior(), BorderLayout.NORTH);
        
        // Panel central con tabla
        add(crearPanelTabla(), BorderLayout.CENTER);
        
        // Panel inferior con información
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }
    
    /**
     * Crea el panel superior con título y botones de acción
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        // Título
        JLabel titulo = new JLabel("👥 Gestión de Alumnos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titulo, BorderLayout.WEST);
        
        // Panel de búsqueda y botones
        JPanel panelDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelDerecha.setBackground(Color.WHITE);
        
        // Búsqueda
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));
        txtBuscar.addActionListener(e -> buscarAlumno());
        
        JButton btnBuscar = crearBoton("🔍 Buscar", COLOR_PRIMARY);
        btnBuscar.addActionListener(e -> buscarAlumno());
        
        panelDerecha.add(new JLabel("Buscar:"));
        panelDerecha.add(txtBuscar);
        panelDerecha.add(btnBuscar);
        
        // Botón nuevo
        btnNuevo = crearBoton("➕ Nuevo Alumno", new Color(25, 135, 84));
        btnNuevo.addActionListener(e -> mostrarFormularioNuevo());
        panelDerecha.add(btnNuevo);
        
        panel.add(panelDerecha, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Crea el panel con la tabla de alumnos
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Crear modelo de tabla
        String[] columnas = {"CUI", "Nombre Completo", "DNI", "Fecha Nac.", "Carreras", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        
        // Crear tabla
        tablaAlumnos = new JTable(modeloTabla);
        tablaAlumnos.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaAlumnos.setRowHeight(30);
        tablaAlumnos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaAlumnos.getTableHeader().setBackground(COLOR_PRIMARY);
        tablaAlumnos.getTableHeader().setForeground(Color.WHITE);
        tablaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Ajustar ancho de columnas
        tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaAlumnos.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaAlumnos.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaAlumnos.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaAlumnos.getColumnModel().getColumn(4).setPreferredWidth(150);
        tablaAlumnos.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(tablaAlumnos);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel inferior con botones de acción
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);
        
        btnVer = crearBoton("👁 Ver Detalle", COLOR_PRIMARY);
        btnVer.addActionListener(e -> verDetalleAlumno());
        
        btnEditar = crearBoton("✏️ Editar", new Color(255, 193, 7));
        btnEditar.addActionListener(e -> editarAlumno());
        
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(220, 53, 69));
        btnEliminar.addActionListener(e -> eliminarAlumno());
        
        panel.add(btnVer);
        panel.add(btnEditar);
        panel.add(btnEliminar);
        
        return panel;
    }
    
    /**
     * Crea un botón con estilo
     */
    private JButton crearBoton(String texto, Color color) {
        JButton button = new JButton(texto);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    /**
     * Actualiza la tabla con los datos de alumnos
     */
    public void actualizarTabla() {
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        // Obtener alumnos
        List<Alumno> alumnos = service.listarAlumnos();
        
        // Llenar tabla
        for (Alumno alumno : alumnos) {
            Object[] fila = new Object[6];
            fila[0] = alumno.obtenerCui();
            fila[1] = alumno.getNombreCompleto();
            fila[2] = alumno.getDNI();
            fila[3] = alumno.getFechaNacimiento().toString();
            fila[4] = alumno.getCarrerasInscripto().size() + " carrera(s)";
            fila[5] = String.format("%.2f", alumno.obtenerPromedioGeneral());
            
            modeloTabla.addRow(fila);
        }
    }
    
    /**
     * Busca un alumno por CUI, nombre o DNI
     */
    private void buscarAlumno() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        
        if (busqueda.isEmpty()) {
            actualizarTabla();
            return;
        }
        
        // Limpiar tabla
        modeloTabla.setRowCount(0);
        
        // Filtrar alumnos
        List<Alumno> alumnos = service.listarAlumnos();
        for (Alumno alumno : alumnos) {
            boolean coincide = alumno.obtenerCui().toLowerCase().contains(busqueda) ||
                             alumno.getNombreCompleto().toLowerCase().contains(busqueda) ||
                             alumno.getDNI().contains(busqueda);
            
            if (coincide) {
                Object[] fila = new Object[6];
                fila[0] = alumno.obtenerCui();
                fila[1] = alumno.getNombreCompleto();
                fila[2] = alumno.getDNI();
                fila[3] = alumno.getFechaNacimiento().toString();
                fila[4] = alumno.getCarrerasInscripto().size() + " carrera(s)";
                fila[5] = String.format("%.2f", alumno.obtenerPromedioGeneral());
                
                modeloTabla.addRow(fila);
            }
        }
    }
    
    /**
     * Muestra el formulario para crear un nuevo alumno
     */
    private void mostrarFormularioNuevo() {
        AlumnoFormDialog dialog = new AlumnoFormDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this),
            service,
            null
        );
        dialog.setVisible(true);
        
        if (dialog.isGuardado()) {
            actualizarTabla();
            JOptionPane.showMessageDialog(
                this,
                "Alumno registrado exitosamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    /**
     * Muestra el detalle de un alumno seleccionado
     */
    private void verDetalleAlumno() {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un alumno de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String cui = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Alumno alumno = service.buscarAlumnoPorCUI(cui);
        
        if (alumno != null) {
            AlumnoDetalleDialog dialog = new AlumnoDetalleDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                alumno
            );
            dialog.setVisible(true);
        }
    }
    
    /**
     * Edita un alumno seleccionado
     */
    private void editarAlumno() {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un alumno de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String cui = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        Alumno alumno = service.buscarAlumnoPorCUI(cui);
        
        if (alumno != null) {
            AlumnoFormDialog dialog = new AlumnoFormDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                service,
                alumno
            );
            dialog.setVisible(true);
            
            if (dialog.isGuardado()) {
                actualizarTabla();
                JOptionPane.showMessageDialog(
                    this,
                    "Alumno actualizado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
    
    /**
     * Elimina un alumno seleccionado
     */
    private void eliminarAlumno() {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Por favor, seleccione un alumno de la tabla",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String cui = (String) modeloTabla.getValueAt(filaSeleccionada, 0);
        String nombre = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al alumno " + nombre + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean eliminado = service.eliminarAlumno(cui);
            
            if (eliminado) {
                actualizarTabla();
                JOptionPane.showMessageDialog(
                    this,
                    "Alumno eliminado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Error al eliminar el alumno",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
