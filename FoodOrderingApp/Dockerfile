# Stage 1: Build the Angular application
FROM node:20-alpine AS build

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install all dependencies (including dev dependencies needed for build)
RUN npm ci

# Copy source code
COPY . .

# Build the application for production
RUN npm run build

# Stage 2: Serve the built application with nginx
FROM nginx:alpine

# Copy built application from build stage (Angular 17+ puts files in browser/ subdirectory)
COPY --from=build /app/dist/FoodOrderingApp/browser /usr/share/nginx/html

# Copy nginx configuration (optional - for API proxying)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
