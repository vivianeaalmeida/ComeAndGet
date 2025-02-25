# Use a Node base image
FROM node:18-alpine

# Create and use /app as the working directory
WORKDIR /app

# Install dependencies (so it skips this steps if you didn't change dependencies)
RUN npm install -g @angular/cli@17

# Copy package files first (for caching, this is useful incase you change code but not the dependencies)
COPY package*.json ./


RUN npm install

# Expose the Angular dev server port
EXPOSE 4200

# Start the dev server (ng serve for development, ng build for prod)
# CMD ["npx", "ng", "serve", "--host", "0.0.0.0"]
