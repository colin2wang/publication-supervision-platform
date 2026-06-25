# Publication Intelligent Supervision Platform

A comprehensive government regulatory system for news and publication supervision, leveraging AI, big data, and multimodal recognition to build an intelligent supervision chain covering identification, circulation tracking, and public opinion analysis.

## Project Structure

```
platform-all/
├── db/                          # PostgreSQL DDL/DML scripts
│   ├── 01_create_database.sql   # Database & user creation
│   ├── 02_create_tables_sys.sql # System tables (user, role, menu, dept...)
│   ├── 03_create_tables_idt.sql # Identification platform tables
│   ├── 04_create_tables_circ.sql# Circulation supervision tables
│   ├── 05_create_tables_opi.sql # Opinion analysis tables
│   ├── 06_create_tables_ai.sql  # AI platform tables
│   ├── 07_init_dict_data.sql    # Dictionary data
│   ├── 08_init_menu_data.sql    # Menu tree with permissions
│   ├── 09_init_role_data.sql    # Default roles
│   └── 10_init_admin_data.sql   # Admin user (admin/admin123)
├── backend/pub-supervision-server/  # Spring Boot 3 backend
│   ├── pom.xml
│   └── src/main/java/com/pub/supervision/
│       ├── PubSupervisionApplication.java
│       ├── config/              # Security, MyBatis-Plus, CORS, JWT
│       ├── common/              # R, PageResult, exceptions, utils
│       ├── entity/              # 57 entity classes (sys/idt/circ/opi/ai)
│       ├── mapper/              # 57 MyBatis-Plus mappers
│       ├── service/             # 35 service interfaces
│       ├── service/impl/        # 35 service implementations
│       └── controller/          # 33 REST controllers
├── frontend/pub-supervision-web/    # Vue 3 frontend
│   ├── package.json
│   ├── vite.config.ts
│   └── src/
│       ├── api/                 # API modules (auth, identification, circulation, opinion, ai, system)
│       ├── router/              # Vue Router with 28 routes
│       ├── store/               # Pinia stores (user, app)
│       ├── components/          # MainLayout
│       ├── views/               # 27 page components
│       ├── types/               # TypeScript definitions
│       └── utils/               # Request, auth, helpers
├── docs/                        # Design documentation
│   ├── 01-Project Architecture.md
│   ├── 02-Detailed Design.md
│   ├── 03-Database Design.md
│   ├── 04-API Design.md
│   └── 05-Page Design.md
└── publication-supervision-platform.zip  # Packaged release
```

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | Vue 3.4 + TypeScript + Vite 5 + Ant Design Vue 4 + Pinia + ECharts 5 |
| Backend | Spring Boot 3.2 + Spring Security + JWT + MyBatis-Plus 3.5 |
| Database | PostgreSQL 16+ |
| Build | Maven (backend) + pnpm (frontend) |

## Quick Start

### 1. Database

```bash
# Connect to PostgreSQL and run scripts in order
psql -U postgres -f db/01_create_database.sql
psql -U postgres -d pub_supervision -f db/02_create_tables_sys.sql
psql -U postgres -d pub_supervision -f db/03_create_tables_idt.sql
psql -U postgres -d pub_supervision -f db/04_create_tables_circ.sql
psql -U postgres -d pub_supervision -f db/05_create_tables_opi.sql
psql -U postgres -d pub_supervision -f db/06_create_tables_ai.sql
psql -U postgres -d pub_supervision -f db/07_init_dict_data.sql
psql -U postgres -d pub_supervision -f db/08_init_menu_data.sql
psql -U postgres -d pub_supervision -f db/09_init_role_data.sql
psql -U postgres -d pub_supervision -f db/10_init_admin_data.sql
```

### 2. Backend

```bash
cd backend/pub-supervision-server
mvn spring-boot:run
# Starts at http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
```

### 3. Frontend

```bash
cd frontend/pub-supervision-web
pnpm install
pnpm dev
# Starts at http://localhost:3000
```

### 4. Login

- URL: http://localhost:3000
- Username: `admin`
- Password: `admin123`

## Business Modules

### Identification Platform (智能辅助鉴定)
- Publication management with ISBN lookup
- Identification task workflow (create → assign → identify → review → archive)
- Illegal sample management and comparison
- Dataset management for AI training
- Law enforcement docking

### Circulation Supervision (出版物流转监管)
- Merchant management with risk scoring
- Qualification verification and expiry alerts
- Package tracking with node validation
- Alert system (red/orange/yellow/blue levels)
- Black/white/gray list management
- Supervision dashboard with ECharts

### Opinion Analysis (智能舆情分析)
- Multi-channel collection tasks (Weibo, WeChat, Douyin, News...)
- Public opinion monitoring with sentiment analysis
- Event tracking and correlation analysis
- AI-powered report generation
- Effectiveness statistics

### AI Platform (AI中台)
- Knowledge base with vector search
- Model management and testing
- Agent orchestration
- Digital human interaction
- Smart proofreading and sensitive content detection
- Multi-language translation

## API Reference

All APIs follow RESTful conventions with prefix `/api/v1/`:

| Module | Base Path | Auth |
|--------|-----------|------|
| Auth | `/api/v1/auth/**` | No |
| System | `/api/v1/system/**` | JWT |
| Identification | `/api/v1/identification/**` | JWT |
| Circulation | `/api/v1/circulation/**` | JWT |
| Opinion | `/api/v1/opinion/**` | JWT |
| AI | `/api/v1/ai/**` | JWT |

## Default Accounts

| Role | Username | Password | Permissions |
|------|----------|----------|-------------|
| Admin | admin | admin123 | Full access |
| Identifier | identifier | identifier123 | Identification module |
| Reviewer | reviewer | reviewer123 | Review operations |
| Operator | operator | operator123 | Circulation module |
| Analyst | analyst | analyst123 | Opinion module |

## License

Internal use only.
