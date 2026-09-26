# ==========================================
# Etapa 1: Compilación de la SPA con Node
# ==========================================
FROM node:22-alpine AS builder

WORKDIR /app

# Copiar manifiestos primero para cachear dependencias
COPY package*.json ./
RUN npm ci

# Copiar el código fuente y compilar para producción
COPY . .
RUN npm run build -- --configuration production

# ==========================================
# Etapa 2: Servidor Web Nginx liviano
# ==========================================
FROM nginx:alpine AS runner

# Copiar configuración de Nginx con soporte para SPA y proxy inverso
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copiar los artefactos estáticos generados
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
