// package com.microrecetas.usuarios.controller;

// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("/recetas")
// public class RecetasController {
    
//     @GetMapping("/detalle")
//     public String getMethodName(@RequestParam String param) {
//         return new String();
//     }
    
//     @GetMapping("/busqueda")
//     public String getMethodNameWithParam(@RequestParam String param) {
//         return new String();
//     }

//     @GetMapping("/categorias")
//     public String getRecetaByCategoria(@RequestParam(value="name", defaultValue="World") String name) {
//         return "Categorias de recetas: " + name;
//     }

//     @PostMapping("/nueva")
//     public String nueva(@RequestParam(value="name", defaultValue="World") String name) {
//         return "Proximo detalle de recetas";
//     }

//     @PostMapping("nuevareceta")
//     public String postMethodName(@RequestBody String entity) {
//         //TODO: process POST request
        
//         return entity;
//     }
    
// }
