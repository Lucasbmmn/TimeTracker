CREATE TABLE Clients (
    id TEXT PRIMARY KEY,
    company TEXT NOT NULL,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    phone_number TEXT NOT NULL,
    timezone TEXT NOT NULL
);

CREATE TABLE Projects (
    id TEXT PRIMARY KEY,
    client_id TEXT,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    estimated_time INTEGER,
    hourly_price REAL,
    fixed_price REAL,
    created_at INTEGER NOT NULL,
    deadline INTEGER,
    FOREIGN KEY (client_id) REFERENCES Clients(id)
);

CREATE TABLE Task_Statuses (
    id TEXT PRIMARY KEY,
    label TEXT NOT NULL
);

CREATE TABLE Task_Types (
    id TEXT PRIMARY KEY,
    label TEXT NOT NULL
);

CREATE TABLE Tasks (
    id TEXT PRIMARY KEY,
    project_id TEXT NOT NULL,
    task_status_id TEXT,
    task_type_id TEXT,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    estimated_time INTEGER,
    created_at INTEGER NOT NULL,
    FOREIGN KEY (project_id) REFERENCES Projects(id),
    FOREIGN KEY (task_status_id) REFERENCES Task_Statuses(id),
    FOREIGN KEY (task_type_id) REFERENCES Task_Types(id)
);

CREATE TABLE Project_Time_Entries (
    id TEXT PRIMARY KEY,
    project_id TEXT NOT NULL,
    duration INTEGER NOT NULL,
    created_at INTEGER NOT NULL,
    isBillable INTEGER NOT NULL,
    FOREIGN KEY (project_id) REFERENCES Projects(id)
);

CREATE TABLE Task_Time_Entries (
    id TEXT PRIMARY KEY,
    task_id TEXT NOT NULL,
    duration INTEGER NOT NULL,
    created_at INTEGER NOT NULL,
    FOREIGN KEY (task_id) REFERENCES Tasks(id)
);
