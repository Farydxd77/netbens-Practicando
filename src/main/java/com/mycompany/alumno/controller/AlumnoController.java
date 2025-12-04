/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.alumno.controller;

import com.mycompany.alumno.dao.AlumnoDAO;
import com.mycompany.alumno.model.Alumno;
import java.util.List;

/**
 * CAPA CONTROLADOR - Lógica de negocio
 * Intermedio entre la vista y el DAO
 */
public class AlumnoController {
    
    private AlumnoDAO alumnoDAO;
    
    public AlumnoController() {
        this.alumnoDAO = new AlumnoDAO();
    }
    
    // Guardar un nuevo alumno con validaciones
    public boolean guardarAlumno(String nombre, String apellido, String email, Integer edad, String carrera) {
        try {
            // Validaciones básicas
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            if (apellido == null || apellido.trim().isEmpty()) {
                throw new IllegalArgumentException("El apellido no puede estar vacío");
            }
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException("Email inválido");
            }
            if (edad != null && (edad < 0 || edad > 120)) {
                throw new IllegalArgumentException("Edad inválida");
            }
            
            Alumno alumno = new Alumno(nombre, apellido, email, edad, carrera);
            alumnoDAO.crear(alumno);
            return true;
        } catch (Exception e) {
            System.err.println("Error en controlador al guardar: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener todos los alumnos
    public List<Alumno> obtenerTodosLosAlumnos() {
        return alumnoDAO.obtenerTodos();
    }
    
    // Obtener alumno por ID
    public Alumno obtenerAlumnoPorId(Long id) {
        return alumnoDAO.obtenerPorId(id);
    }
    
    // Actualizar alumno
    public boolean actualizarAlumno(Long id, String nombre, String apellido, String email, Integer edad, String carrera) {
        try {
            Alumno alumno = alumnoDAO.obtenerPorId(id);
            if (alumno == null) {
                throw new IllegalArgumentException("Alumno no encontrado");
            }
            
            // Validaciones
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío");
            }
            if (apellido == null || apellido.trim().isEmpty()) {
                throw new IllegalArgumentException("El apellido no puede estar vacío");
            }
            
            alumno.setNombre(nombre);
            alumno.setApellido(apellido);
            alumno.setEmail(email);
            alumno.setEdad(edad);
            alumno.setCarrera(carrera);
            
            alumnoDAO.actualizar(alumno);
            return true;
        } catch (Exception e) {
            System.err.println("Error en controlador al actualizar: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar alumno
    public boolean eliminarAlumno(Long id) {
        try {
            alumnoDAO.eliminar(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error en controlador al eliminar: " + e.getMessage());
            return false;
        }
    }
    
    // Buscar alumnos por nombre
    public List<Alumno> buscarAlumnosPorNombre(String nombre) {
        return alumnoDAO.buscarPorNombre(nombre);
    }
}