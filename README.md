# Desafío: Integración de API de OpenRouter - Arquitecto de Software IA

API REST desarrollada en **Java (Spring Boot)** que automatiza la toma de decisiones de arquitectura de software. El sistema recibe Requisitos Funcionales (RF) y No Funcionales (RNF), consulta a un LLM vía **OpenRouter**, y devuelve una recomendación técnica estructurada y justificada bajo el estándar **ISO/IEC 25010**.

##  Tabla de Contenidos
1. [Pre-requisitos](#-pre-requisitos)
2. [Evidencias de Ejecución](#-evidencias-de-ejecución)
3. [Configuración Local (.env)](#-configuración-local-env)

---

##  Pre-requisitos

Asegúrate de tener instalado en tu entorno:
* **Java JDK 21** (o superior).
* **Maven** (Gestor de dependencias).
* **Node.js & npm** (Requerido solo para ejecutar las pruebas de Cypress).
* Una **API Key** válida de [OpenRouter](https://openrouter.ai/).

---

## Evidencias de Ejecución

Link del video del video con ejecucion de toda la tarea
https://drive.google.com/file/d/18QUWdyXvpQiie1A15Bzp1nmdN2pdQTJx/view?usp=sharing

---

##  Configuración Local (.env)

Por seguridad, las credenciales **NO** se suben al repositorio. Debes configurar tu API Key localmente.

### 1. Archivo `.env`
Crea un archivo llamado `.env` en la raíz del proyecto y agrega tu llave:

```properties
# Configuración de OpenRouter
OPENROUTER_API_KEY=sk-or-v1-tu-clave-secreta-aqui
