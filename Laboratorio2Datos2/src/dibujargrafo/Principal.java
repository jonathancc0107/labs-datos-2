/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dibujargrafo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jesusdavide
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    ArrayList<Nodo> lista = new ArrayList();
    ArrayList<Arista> aristas = new ArrayList<>();
    ArrayList<String> esp = new ArrayList<>();
    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> fr = new ArrayList<>();
    ArrayList<String> port = new ArrayList<>();
    ArrayList<String> ger = new ArrayList<>();
    ArrayList<String> chn = new ArrayList<>();
    ArrayList<String> familia;
    ArrayList<String> amigos;
    ArrayList<String> amigosdeamigos;
    ArrayList<String> relacionados;
    BufferedImage iconohombre = Metodos.cargarImagen("icon-man");
    BufferedImage iconomujer = Metodos.cargarImagen("icon-girl");
    BufferedImage hombreSeleccionado = Metodos.cargarImagen("man-selected");
    BufferedImage mujerSeleccionado = Metodos.cargarImagen("girl-selected");
    BufferedImage hombreresaltado = Metodos.cargarImagen("man-res");
    BufferedImage mujerresaltada = Metodos.cargarImagen("girl-res");
    Nodo seleccionado = null;
    String sexo;
    public static final String[] sexos = {"Masculino", "Femenino"};
    public static final String[] relaciones = {"Amigo(a)", "Padre", "Madre", "Hermano(a)",
        "Primo(a)", "Sobrino(a)", "Abuelo(a)", "Tío(a)"};
    boolean entrar = true;

    /* Método utilizado para mostrar el jOptionPane que contiene los
        checkbox de los idiomas, y guarda el nombre de la persona en los
        ArrayList de los idiomas seleccionados */
    void mostraridiomas(String name) {
        JCheckBox ES = new JCheckBox("Español");
        JCheckBox EN = new JCheckBox("Inglés");
        JCheckBox FR = new JCheckBox("Francés");
        JCheckBox GER = new JCheckBox("Alemán");
        JCheckBox PR = new JCheckBox("Portugués");
        JCheckBox CH = new JCheckBox("Mandarín");
        String message = "Seleccione los idiomas que habla ";
        Object[] params = {message, ES, EN, FR, GER, PR, CH};
        int n = JOptionPane.showConfirmDialog(null, params, "Idiomas", JOptionPane.YES_OPTION);
        boolean entra;
        do {
            if (ES.isSelected() || EN.isSelected() || FR.isSelected() || CH.isSelected()
                    || GER.isSelected() || PR.isSelected()) {
                if (ES.isSelected()) {
                    esp.add(name);
                }
                if (EN.isSelected()) {
                    eng.add(name);
                }
                if (FR.isSelected()) {
                    fr.add(name);
                }
                if (CH.isSelected()) {
                    chn.add(name);
                }
                if (GER.isSelected()) {
                    ger.add(name);
                }
                if (PR.isSelected()) {
                    port.add(name);
                }
                entra = false;
            } else {
                JOptionPane.showMessageDialog(null, "Debe ingresar al menos un idioma");
                n = JOptionPane.showConfirmDialog(null, params, "Idiomas", JOptionPane.YES_OPTION);
                entra = true;
            }
        } while (entra);
    }

    // Método que dibuja el icono habiendo validado el sexo de la persona
    void dibujar(Nodo n) {
        if (n.isIsman()) {
            Metodos.dibujarIcono(iconohombre, n.getX(), n.getY(), panel);
        } else {
            Metodos.dibujarIcono(iconomujer, n.getX(), n.getY(), panel);
        }
    }

    /* Método que dibuja el icono habiendo validado el sexo de la persona 
    seleccionada */
    void dibujarselec(Nodo n) {
        if (n.isIsman()) {
            Metodos.dibujarIcono(hombreSeleccionado, n.getX(), n.getY(), panel);
        } else {
            Metodos.dibujarIcono(mujerSeleccionado, n.getX(), n.getY(), panel);
        }
    }

    /* Método que dibuja el icono resaltado para diferenciar entre su color
    normal y seleccionado*/
    void dibujarresaltado(Nodo n) {
        if (n.isIsman()) {
            Metodos.dibujarIcono(hombreresaltado, n.getX(), n.getY(), panel);
        } else {
            Metodos.dibujarIcono(mujerresaltada, n.getX(), n.getY(), panel);
        }
    }

    /* Método utilizado para revisar el nombre de cada persona por nodo
    y comparar con los nombres registrados en el arraylist del idioma */
    void comparar(ArrayList<String> a) {
        if (!a.isEmpty()) {
            for (Nodo nodo : lista) {
                dibujar(nodo);
                for (int i = 0; i < a.size(); i++) {
                    String nombre = a.get(i);
                    if (nodo.getNombre().equals(nombre)) {
                        dibujarresaltado(nodo);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ninguna persona habla este idioma");
        }
    }

    /* Método que verifica el idioma seleccionado en el combobox para
    poder usar el método comparar con dicho idioma */
    void BuscarIdioma() {
        String idioma = CBIdiom.getSelectedItem().toString();
            switch (idioma) {
                case "Español":
                    comparar(esp);
                    break;

                case "Inglés":
                    comparar(eng);
                    break;

                case "Alemán":
                    comparar(ger);
                    break;

                case "Francés":
                    comparar(fr);
                    break;

                case "Portugués":
                    comparar(port);
                    break;

                case "Mandarín":
                    comparar(chn);
                    break;
            }
    }

    /* Método que resalta el icono de las personas con el sexo x seleccionado */
    void resaltarsex(String s) {
        if (s.equals("Masculino")) {
            for (Nodo nodo : lista) {
                if (nodo.isIsman()) {
                    dibujarresaltado(nodo);
                } else {
                    dibujar(nodo);
                }
            }
        } else {
            for (Nodo nodo : lista) {
                dibujar(nodo);
                if (!nodo.isIsman()) {
                    dibujarresaltado(nodo);
                } else {
                    dibujar(nodo);
                }
            }
        }
    }

    /* Método que resalta todos los familiares de la persona seleccionada */
    void familiares() {
        String nodoI = seleccionado.getNombre();
        familia = new ArrayList<>();
        if (!aristas.isEmpty()) {
            for (Arista arista : aristas) {
                if ((nodoI.equals(arista.getNodoI()) || nodoI.equals(arista.getNodoD()))
                        && !arista.getRelation().equals("Amigo(a)")) {
                    if (nodoI.equals(arista.getNodoI())) {
                        familia.add(arista.getNodoD());
                    } else {
                        familia.add(arista.getNodoI());
                    }
                }
            }
            if (!familia.isEmpty()) {
                for (Nodo nodo : lista) {
                    for (int i = 0; i < familia.size(); i++) {
                        if (nodo.getNombre().equals(familia.get(i))) {
                            dibujarresaltado(nodo);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La persona seleccionada no tiene familiares");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe haber al menos una arista");
        }
    }

    /* Método que resalta todas las personas no relacionadas a la 
    persona seleccionada */
    void norelation() {
        String nodoI = seleccionado.getNombre();
        relacionados = new ArrayList<>();
        if (!aristas.isEmpty()) {
            for (Arista arista : aristas) {
                if (nodoI.equals(arista.getNodoI()) || nodoI.equals(arista.getNodoD())) {
                    if (nodoI.equals(arista.getNodoI())) {
                        relacionados.add(arista.getNodoD());
                    } else {
                        relacionados.add(arista.getNodoI());
                    }
                }
            }
            if (relacionados.size() == lista.size() - 1) {
                JOptionPane.showMessageDialog(null, "La persona seleccionada está relacionada"
                        + "con todas las otras personas");
            } else {
                if (relacionados.isEmpty()) {
                    for (Nodo nodo : lista) {
                        if (!nodo.equals(seleccionado)) {
                            dibujarresaltado(nodo);
                        }
                    }
                } else {
                    for (Nodo nodo : lista) {
                        for (int i = 0; i < relacionados.size(); i++) {
                            if (!nodo.getNombre().equals(relacionados.get(i))
                                    && !nodo.equals(seleccionado)) {
                                dibujarresaltado(nodo);
                            }
                        }
                    }
                }

            }
        } else {
            for (Nodo nodo : lista) {
                if (!nodo.getNombre().equals(nodoI)) {
                    dibujarresaltado(nodo);
                }
            }
        }
    }

    /* Método que resalta todos los amigos de la persona seleccionada */
    void amigos() {
        String nodoI = seleccionado.getNombre();
        amigos = new ArrayList<>();
        if (!aristas.isEmpty()) {
            for (Arista arista : aristas) {
                if ((nodoI.equals(arista.getNodoI()) || nodoI.equals(arista.getNodoD()))
                        && arista.getRelation().equals("Amigo(a)")) {
                    if (nodoI.equals(arista.getNodoI())) {
                        amigos.add(arista.getNodoD());
                    } else {
                        amigos.add(arista.getNodoI());
                    }
                }
            }
            if (!amigos.isEmpty()) {
                for (Nodo nodo : lista) {
                    for (int i = 0; i < amigos.size(); i++) {
                        if (nodo.getNombre().equals(amigos.get(i))) {
                            dibujarresaltado(nodo);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "La persona seleccionada no tiene amigos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe haber al menos una arista");
        }
    }

    /* Método que resalta todos los amigos recomedados para la 
    persona seleccionada basándose en los amigos de sus amigos */
    void amigosrecomendados() {
        String nodoI = seleccionado.getNombre();
        amigos = new ArrayList<>();
        amigosdeamigos = new ArrayList<>();
        if (!aristas.isEmpty()) {
            for (Arista arista : aristas) {
                if ((nodoI.equals(arista.getNodoI()) || nodoI.equals(arista.getNodoD()))
                        && arista.getRelation().equals("Amigo(a)")) {
                    if (nodoI.equals(arista.getNodoI())) {
                        amigos.add(arista.getNodoD());
                    } else {
                        amigos.add(arista.getNodoI());
                    }
                }
            }
            if (!amigos.isEmpty()) {
                for (Nodo nodo : lista) {
                    String nombrenodo = nodo.getNombre();
                    for (int i = 0; i < amigos.size(); i++) {
                        if (nombrenodo.equals(amigos.get(i))) {
                            for (Arista arista : aristas) {
                                if ((nombrenodo.equals(arista.getNodoI())
                                        || nombrenodo.equals(arista.getNodoD()))
                                        && arista.getRelation().equals("Amigo(a)")) {
                                    if (nodo.getNombre().equals(arista.getNodoI())) {
                                        amigosdeamigos.add(arista.getNodoD());
                                    } else {
                                        amigosdeamigos.add(arista.getNodoI());
                                    }
                                }
                            }
                        }
                    }
                }
                if (!amigosdeamigos.isEmpty()) {
                    for (Nodo nodo : lista) {
                        for (int i = 0; i < amigosdeamigos.size(); i++) {
                            if (nodo.getNombre().equals(amigosdeamigos.get(i))
                                    && !nodo.equals(seleccionado)) {
                                dibujarresaltado(nodo);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hay amigos sugeridos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La persona seleccionada no tiene amigos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe haber al menos una arista");
        }
    }

    public Principal() throws IOException {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Opciones = new javax.swing.ButtonGroup();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        AllFam = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        AllAmi = new javax.swing.JRadioButton();
        AllNoR = new javax.swing.JRadioButton();
        NewFriends = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        AllSex = new javax.swing.JRadioButton();
        AllIdiom = new javax.swing.JRadioButton();
        CBSex = new javax.swing.JComboBox<>();
        CBIdiom = new javax.swing.JComboBox<>();
        Mostrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 1024, 768));

        panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panel.setPreferredSize(new java.awt.Dimension(800, 580));
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                panelMouseReleased(evt);
            }
        });
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                panelComponentShown(evt);
            }
        });
        panel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                panelPropertyChange(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/mapa.png"))); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        Opciones.add(AllFam);
        AllFam.setText("Todos los familiares de la persona seleccionada");

        jLabel2.setText("Para proceder con las siguientes opciones debe seleccionar a la persona dentro del mapa");

        Opciones.add(AllAmi);
        AllAmi.setText("Todos los amigos (No familares) de la persona seleccionada");

        Opciones.add(AllNoR);
        AllNoR.setText("Personas sin ninguna relación con la persona seleccionada");

        Opciones.add(NewFriends);
        NewFriends.setText("Sugerir amigos a la persona seleccionada");

        jLabel3.setText("Para estas opciones no es necesario seleccionar a una persona, pero sí el sexo o el idioma");

        Opciones.add(AllSex);
        AllSex.setText("Todas las personas del sexo:");

        Opciones.add(AllIdiom);
        AllIdiom.setText("Todas las personas que hablan el idioma:");

        CBSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        CBIdiom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Español", "Inglés", "Francés", "Alemán", "Mandarín", "Portugués" }));

        Mostrar.setText("Confirmar");
        Mostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MostrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AllIdiom)
                        .addGap(18, 18, 18)
                        .addComponent(CBIdiom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AllSex)
                        .addGap(18, 18, 18)
                        .addComponent(CBSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(NewFriends)
                    .addComponent(AllNoR)
                    .addComponent(AllAmi)
                    .addComponent(jLabel2)
                    .addComponent(AllFam)
                    .addComponent(jLabel3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Mostrar)
                        .addGap(155, 155, 155)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(AllFam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AllAmi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AllNoR)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(NewFriends)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AllSex)
                    .addComponent(CBSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AllIdiom)
                    .addComponent(CBIdiom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(Mostrar)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseReleased
        JFrame frame = new JFrame("El necesario");
        int d = 50, r = d / 2;
        int x = evt.getX();
        int y = evt.getY();
        int ancho = panel.getWidth(), alto = panel.getHeight();
        if (seleccionado == null) {
            seleccionado = Metodos.seleccionar(x, y, d, lista);
            if (seleccionado != null) {
                dibujarselec(seleccionado);
            } else {
                if (Metodos.sePuedeDibujar(x, y, r, ancho, alto)) {
                    if (!Metodos.colisiona(x, y, d, lista)) {
                        String nombre = JOptionPane.showInputDialog("Ingrese su nombre: ");
                        String sexo = (String) JOptionPane.showInputDialog(frame,
                                "Por favor seleccione su sexo",
                                "Seleccione su sexo",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                sexos,
                                sexos[0]);
                        do {
                            if (nombre.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Error, no puede dejar la casilla vacía");
                                nombre = JOptionPane.showInputDialog("Ingrese su nombre: ");
                                entrar = true;
                            } else {
                                mostraridiomas(nombre);
                                if (sexo.equals("Masculino")) {
                                    Metodos.dibujarIcono(iconohombre, x, y, panel, nombre);
                                    lista.add(new Nodo(x, y, d, nombre, true));
                                } else {
                                    Metodos.dibujarIcono(iconomujer, x, y, panel, nombre);
                                    lista.add(new Nodo(x, y, d, nombre, false));
                                }
                                entrar = false;
                            }
                        } while (entrar);
                    }
                }
            }
        } else {
            Nodo destino = Metodos.seleccionar(x, y, d, lista);
            if (destino != null && !destino.equals(seleccionado)) {

                Metodos.dibujarLinea(seleccionado.getX(), seleccionado.getY(),
                        destino.getX(), destino.getY(), panel);
                dibujar(destino);
                String relation = (String) JOptionPane.showInputDialog(frame,
                        "¿Qué relación tiene con la persona " + destino.getNombre(),
                        "Relaciones",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        relaciones,
                        relaciones[0]);
                aristas.add(new Arista(seleccionado.getNombre(), destino.getNombre(), relation));
            }
            dibujar(seleccionado);
            seleccionado = null;
        }
    }//GEN-LAST:event_panelMouseReleased

    private void panelComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_panelComponentShown

    }//GEN-LAST:event_panelComponentShown

    private void panelPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_panelPropertyChange

    }//GEN-LAST:event_panelPropertyChange

    private void MostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MostrarActionPerformed
        for (Nodo nodo : lista) {
            if (!nodo.equals(seleccionado)) {
                dibujar(nodo);
            }
        }
        if (Opciones.getSelection() == null) {
            JOptionPane.showMessageDialog(null,
                    "Debe elegir alguna opción");
        } else {
            if (!lista.isEmpty()) {
                if (AllIdiom.isSelected()) {
                    BuscarIdioma();
                } else {
                    if (AllSex.isSelected()) {
                        String s = CBSex.getSelectedItem().toString();
                        resaltarsex(s);
                    } else {
                        if (seleccionado != null) {
                            if (AllFam.isSelected()) {
                                familiares();
                            } else {
                                if (AllAmi.isSelected()) {
                                    amigos();
                                } else {
                                    if (AllNoR.isSelected()) {
                                        norelation();
                                    } else {
                                        if (NewFriends.isSelected()) {
                                            amigosrecomendados();
                                        }
                                    }
                                }
                            }
                            seleccionado = null;
                        } else {
                            JOptionPane.showMessageDialog(null, "Debe seleccionar a alguna persona");
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe haber por lo menos una persona"
                        + "en la lista para usar alguna opción");
            }

        }

    }//GEN-LAST:event_MostrarActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Principal().setVisible(true);

                } catch (IOException ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton AllAmi;
    private javax.swing.JRadioButton AllFam;
    private javax.swing.JRadioButton AllIdiom;
    private javax.swing.JRadioButton AllNoR;
    private javax.swing.JRadioButton AllSex;
    private javax.swing.JComboBox<String> CBIdiom;
    private javax.swing.JComboBox<String> CBSex;
    private javax.swing.JButton Mostrar;
    private javax.swing.JRadioButton NewFriends;
    private javax.swing.ButtonGroup Opciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
}
