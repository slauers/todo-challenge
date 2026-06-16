# Stage 1: Build with Node
FROM node:22-alpine AS builder

WORKDIR /app

# Accept VITE_API_URL as build argument
ARG VITE_API_URL=http://localhost:8080

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the application with VITE_API_URL
ENV VITE_API_URL=${VITE_API_URL}
RUN npm run build

# Stage 2: Serve with Nginx
FROM nginx:alpine

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy built application from builder stage
COPY --from=builder /app/dist /usr/share/nginx/html

# Expose port
EXPOSE 80

# Nginx runs by default
CMD ["nginx", "-g", "daemon off;"]
