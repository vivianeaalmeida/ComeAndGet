# Use the full .NET SDK image which includes all build and runtime tools.
FROM mcr.microsoft.com/dotnet/sdk:8.0

# Working directory within container, so your app knows where to go from
WORKDIR /app

# Copies your entire .NET project from the "DotNet" folder into the container.
COPY DotNet/ .

# Builds the project (skips tests?)
RUN dotnet build

# Exposes port 5000, which is the default port for the app.
EXPOSE 5000

# Runs the application using "dotnet run" for a simple development experience.
CMD ["dotnet", "run"]

