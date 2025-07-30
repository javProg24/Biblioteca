package Views.Libro;

import com.formdev.flatlaf.FlatLightLaf;
import Controllers.Datos.DbOperaciones.ExecuteQuery;
import Controllers.Operadores.Metodos.ControladorEjemplar;
import Controllers.Operadores.Metodos.ControladorLibro;
import Models.Ejemplar;
import Models.Libro;
import resources.Shared.Notification.NotificationComponent;
import resources.Utils.ComponentFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class LibroForm extends JDialog {
    private int ID_LibroOut;
    private JTextField txtISBN,
            txtTitulo,
            txtAnio,
            txtAutor,
            txtCantidad;
    private JComboBox<String> comboCategoria;

    private boolean isEdit;
    private int idLibro;
    private Runnable onLibroSaved;

    public LibroForm(Frame parent, Runnable onLibroSaved) {
        this(parent, 0, false, onLibroSaved);
    }

    public LibroForm(Frame parent, int id, boolean isEdit, Runnable onLibroSaved) {
        super(parent, isEdit ? "Editar Libro" : "Agregar Libro", true);
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.isEdit = isEdit;
        this.idLibro = id;
        this.onLibroSaved = onLibroSaved;
        //CENTRADO
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        pack();
        setMinimumSize(new Dimension(450, 400));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = getSize();
        int x = (screenSize.width - dialogSize.width) / 2;
        int y = (screenSize.height - dialogSize.height) / 2;
        setLocation(x, y);

        if (isEdit) {
            cargarDatosLibro();
        }
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.add(camposLibro(), BorderLayout.CENTER);
        mainPanel.add(panelBotones(), BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel camposLibro() {
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(ComponentFactory.crearEtiqueta("ISBN"), gbc);
        gbc.gridx = 1;
        txtISBN = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtISBN, gbc);

        // Título
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Título"), gbc);
        gbc.gridx = 1;
        txtTitulo = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtTitulo, gbc);

        // Año de Publicación
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Año de Publicación"), gbc);
        gbc.gridx = 1;
        txtAnio = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAnio, gbc);

        // Autor
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Autor"), gbc);
        gbc.gridx = 1;
        txtAutor = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtAutor, gbc);

        // Categoría
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Categoría"), gbc);
        gbc.gridx = 1;
        comboCategoria = new JComboBox<>(new String[]{"Entretenimiento", "Informativo", "Deportivo", "Educativo", "Artistico", ""});
        panelCampos.add(comboCategoria, gbc);

        // Ejemplares
        gbc.gridx = 0; gbc.gridy++;
        panelCampos.add(ComponentFactory.crearEtiqueta("Cantidad"),gbc);
        gbc.gridx=1;
        txtCantidad = ComponentFactory.crearCampoTexto();
        panelCampos.add(txtCantidad,gbc);

        return panelCampos;
    }

    private JPanel panelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton btnGuardar = ComponentFactory.crearBoton(
                isEdit ? "Editar" : "Agregar",
                isEdit ? ComponentFactory.ruta("editar") : ComponentFactory.ruta("action-add"),true
        );
        JButton btnCancelar = ComponentFactory.crearBoton("Cancelar", ComponentFactory.ruta("action-cancel"),true);

        btnGuardar.addActionListener(e -> guardarLibro());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        return panel;
    }
    private Libro crearLibro() {
        // Validar campos vacíos o nulos
        String isbnText = txtISBN.getText().trim();
        String titulo = txtTitulo.getText().trim();
        String anioText = txtAnio.getText().trim();
        String autor = txtAutor.getText().trim();
        Object categoriaObj = comboCategoria.getSelectedItem();

        if (isbnText.isEmpty() || titulo.isEmpty() || anioText.isEmpty() || autor.isEmpty() || categoriaObj == null) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        int isbn, anio;
        try {
            isbn = Integer.parseInt(isbnText);
            anio = Integer.parseInt(anioText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ISBN y Año deben ser números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Libro libro = new Libro();
        libro.setID(isEdit ? idLibro : 0);
        libro.setISBN(isbn);
        libro.setTitulo(titulo);
        libro.setAnio_Publicacion(anio);
        libro.setAutor(autor);
        libro.setCategoria(categoriaObj.toString());

        return libro;
    }

    private void guardarLibro() {
        try {
            Libro libro = crearLibro();
            if(libro==null){
                return;
            }
            boolean valido = isEdit
                    ? ControladorLibro.actualizarLibro(libro)
                    : ControladorLibro.crearLibro(libro,6);
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            if (valido) {
                NotificationComponent panelComponent = new NotificationComponent(
                        frame,
                        isEdit ? NotificationComponent.Type.INFORMACION : NotificationComponent.Type.EXITO,
                        NotificationComponent.Location.TOP_RIGHT,
                        "Libro " + (isEdit ? "actualizado" : "registrado")
                );
                panelComponent.showNotification();
                ID_LibroOut =ExecuteQuery.intOut;
                obtenerLibro();
                borrarCampos();
                //System.out.println(ExecuteQuery.intOut);
                if (onLibroSaved != null) onLibroSaved.run();
                //dispose();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo guardar el libro.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año o ISBN inválido. Deben ser números.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage());
        }
    }

    private void cargarDatosLibro() {
        Libro libro = new Libro();
        libro.setID(idLibro);
        List<Map<String, Object>> datosLibro = ControladorLibro.obtenerLibroID(libro);
        if (!datosLibro.isEmpty()) {
            Map<String, Object> row = datosLibro.get(0);
            txtISBN.setText(String.valueOf(row.get("ISBN")));
            txtTitulo.setText((String) row.get("Titulo"));
            txtAnio.setText(String.valueOf(row.get("Anio_Publicacion"))); // usa el nombre correcto de la columna
            txtAutor.setText((String) row.get("Autor"));
            comboCategoria.setSelectedItem(row.get("Categoria"));
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el libro.");
            dispose();
        }
    }
    private Ejemplar crearEjemplar(String codigo){
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigo_Interno(codigo);
        ejemplar.setEstado(false);

        ejemplar.setID_Libro(ID_LibroOut);
        return ejemplar;
    }
    private void obtenerLibro(){
        Libro libro = new Libro();
        libro.setID(ID_LibroOut);
        List<Map<String,Object>> datosLibro=ControladorLibro.obtenerLibroID(libro);
        try {
            if(!datosLibro.isEmpty()){
                boolean valido=false;
                Map<String,Object>libroData=datosLibro.get(0);
                String titulo = libroData.get("Titulo").toString();
                int anio = (int) libroData.get("Anio_Publicacion");
                int cantidad = Integer.parseInt(txtCantidad.getText());
                for (int i = 1; i <= cantidad; i++) {
                    String codigo = crearCodigo(ID_LibroOut, anio,ComponentFactory.metodoTitulo(titulo),i);
                    Ejemplar ejemplar = crearEjemplar(codigo);
                    valido = ControladorEjemplar.crearEjemplar(ejemplar);
                }
                Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
                if (valido){
                    NotificationComponent panelComponent = new NotificationComponent(
                            frame,
                            NotificationComponent.Type.EXITO,
                            NotificationComponent.Location.BOTTOM_RIGHT,
                            cantidad+" Ejemplares guardados"
                    );
                    panelComponent.showNotification();
                }
                else {
                    NotificationComponent panelComponent = new NotificationComponent(
                            frame, NotificationComponent.Type.ADVERTENCIA , NotificationComponent.Location.BOTTOM_RIGHT,
                            "Ocurrio un error");
                    panelComponent.showNotification();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private String crearCodigo(int id,int Anio,String titulo,int incremento){
        StringBuilder codigo = new StringBuilder();
        String tituloFormateado = titulo.toUpperCase();
        if (tituloFormateado.length() < 4) {
            tituloFormateado = String.format("%-4s", tituloFormateado).replace(' ', 'X');
        }
        String tituloNuevo = tituloFormateado.substring(0, 4);
        String anioDigitos=String.format("%02d", Anio % 100);
        codigo.append(id);
        codigo.append(anioDigitos);
        codigo.append(tituloNuevo);
        codigo.append(incremento);
        return codigo.toString();
    }
    private void borrarCampos(){
        txtISBN.setText("");
        txtCantidad.setText("");
        txtAnio.setText("");
        txtAutor.setText("");
        txtTitulo.setText("");
        comboCategoria.setSelectedIndex(0);
    }
}