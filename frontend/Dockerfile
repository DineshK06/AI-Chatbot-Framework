# 🔹 Stage 1: Build React App using Node.js
FROM node:18 AS builder

# Set working directory
WORKDIR /app

# Copy package.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy the full source code and build
COPY . .
RUN npm run build

# 🔹 Stage 2: Serve Built App using Nginx
FROM nginx:latest

# Set working directory
WORKDIR /usr/share/nginx/html

# Remove default Nginx static assets
RUN rm -rf ./*

RUN echo "window.REACT_APP_BACKEND_URL='http://backend:8080';" > /usr/share/nginx/html/env.js

# Copy build output from Stage 1
COPY build/ .

# Copy Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80

# Start Nginx server
CMD ["nginx", "-g", "daemon off;"]
