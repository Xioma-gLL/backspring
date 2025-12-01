#!/bin/bash
# Script para ejecutar Spring Boot con variables de entorno
# Uso: ./run-dev.sh

echo "========================================"
echo "  CARGANDO VARIABLES DE ENTORNO"
echo "========================================"

# Cargar variables desde .env.local
if [ -f .env.local ]; then
    export $(grep -v '^#' .env.local | xargs)
    echo "  ✓ Variables cargadas desde .env.local"
else
    echo "  ✗ Archivo .env.local no encontrado"
    exit 1
fi

echo "========================================"
echo "  INICIANDO SPRING BOOT"
echo "========================================"

./mvnw spring-boot:run
