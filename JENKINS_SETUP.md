# Jenkins Configuration Guide - FirstNonRepeatingChar-Build

## Problem Resolved
**Error:** `fatal: couldn't find remote ref refs/heads/main`

**Solution:** Repository now uses `main` as the default branch (was `master`)

---

## Jenkins Job Setup Instructions

### 1. **Create New Pipeline Job**
- Go to Jenkins Dashboard → **New Item**
- Job Name: `FirstNonRepeatingChar-Build`
- Job Type: **Pipeline**
- Click **OK**

### 2. **Pipeline Configuration**

#### **General Section**
- **Description:** `First Non-Repeating Character Maven + JUnit 5 Build Pipeline`
- **GitHub project:** Check this box
- **Project URL:** `https://github.com/NupoorYadu/FirstNonRepeatingChar-Build/`

#### **Build Triggers**
- **Poll SCM:** Check this box
  - Schedule: `H/15 * * * *` (poll every 15 minutes)
- **GitHub hook trigger for GITScm polling:** Check (optional, requires webhook)

#### **Pipeline Section**
- **Definition:** Select **Pipeline script from SCM**
- **SCM:** Git
  - **Repository URL:** `https://github.com/NupoorYadu/FirstNonRepeatingChar-Build.git`
  - **Credentials:** (none required for public repo)
  - **Branch Specifier:** `*/main` ← **IMPORTANT: Ensure this is `main`, not `master`**
  - **Script Path:** `Jenkinsfile`

### 3. **Advanced Repository Settings**
- **Name:** `origin`
- **Refspec:** `+refs/heads/*:refs/remotes/origin/*`
- **Skip tag:** Unchecked

---

## Jenkinsfile Pipeline Stages

The repository includes a complete 8-stage `Jenkinsfile`:

| Stage | Action | Command |
|-------|--------|---------|
| 1 | **Checkout** | Git clone + log latest commits |
| 2 | **Build** | `mvn clean compile` |
| 3 | **Test** | `mvn test` (16 JUnit 5 tests) |
| 4 | **Code Coverage** | `mvn jacoco:report` |
| 5 | **SonarQube Analysis** | `mvn sonar:sonar` *(optional)* |
| 6 | **Package** | `mvn package` |
| 7 | **Artifact Archive** | Store `first-non-repeating-char-1.0.0.jar` |
| 8 | **Build Summary** | Display results & metrics |

---

## Environment Variables (Jenkinsfile)

```groovy
PROJECT_NAME = 'first-non-repeating-char'
BUILD_ARTIFACTS = 'target'
SONAR_HOST_URL = 'http://localhost:9000'
SONAR_LOGIN = 'admin'
```

**To enable SonarQube analysis:**
1. Ensure SonarQube is running at `http://localhost:9000`
2. Uncomment Stage 5 in Jenkinsfile
3. Update `SONAR_LOGIN` token if needed

---

## Test Results Expected

```
Tests run: 16, Failures: 0, Errors: 0, Skipped: 0

Test Categories:
  ✓ Basic non-repeating character detection
  ✓ All characters repeat (returns '\0')
  ✓ Case-insensitive handling
  ✓ Spaces and special character removal
  ✓ Unicode support
  ✓ Real-world examples
  ✓ Insertion order preservation
  ✓ Performance on large strings
  ✓ Edge cases (empty, single char, null)
  ✓ Frequency map verification
  ✓ Documentation tests
```

---

## Code Coverage (JaCoCo)

After successful build, view coverage at:
- **Local:** `target/site/jacoco/index.html`
- **Jenkins Artifacts:** Built-in artifact viewer

**Target Coverage:** >80% line coverage

---

## Troubleshooting

### Issue: "couldn't find remote ref refs/heads/main"
**Solution:** Ensure Jenkinsfile specifies `*/main` in branch specifier (not `*/master`)

### Issue: Build fails with "mvn not found"
**Solution:** Ensure Maven is installed and available in Jenkins agent PATH

### Issue: Tests fail in Jenkins but pass locally
**Solution:** 
- Verify Java version: `java -version` should match pom.xml target
- Check environment in Jenkins: **Manage Jenkins** → **Configure System**

### Issue: SonarQube analysis skipped
**Solution:**
- Uncomment Stage 5 in Jenkinsfile
- Verify SonarQube server is running and accessible
- Update token if needed

---

## GitHub Webhook Setup (Optional - for real-time builds)

1. Go to GitHub repo → **Settings** → **Webhooks** → **Add webhook**
2. **Payload URL:** `http://your-jenkins-url/github-webhook/`
3. **Content type:** `application/json`
4. **Events:** Push events
5. **Active:** ✓ Checked

---

## Sample Successful Build Output

```
========== Checking out source code ==========
Branch main set up to track remote branch main from origin.

========== Building Maven project ==========
[INFO] Building first-non-repeating-char 1.0.0
[INFO] BUILD SUCCESS

========== Running unit tests (16 tests) ==========
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0

========== Running JaCoCo code coverage ==========
[INFO] JaCoCo coverage report generated

========== Packaging ==========
[INFO] Building jar: target/first-non-repeating-char-1.0.0.jar

[SUCCESS] Pipeline executed successfully
```

---

## File References

| File | Purpose |
|------|---------|
| `Jenkinsfile` | Pipeline as Code definition |
| `pom.xml` | Maven build config with JaCoCo & SonarQube |
| `src/main/java/com/example/FirstNonRepeatingChar.java` | Algorithm implementation |
| `src/test/java/com/example/FirstNonRepeatingCharTest.java` | 16 comprehensive tests |
| `README.md` | Project documentation |

---

## Repository Information

- **GitHub:** https://github.com/NupoorYadu/FirstNonRepeatingChar-Build
- **Default Branch:** `main`
- **Language:** Java 11+
- **Build Tool:** Maven 3.6+
- **Testing:** JUnit 5
- **Code Coverage:** JaCoCo 0.8.11

---

## Next Steps

1. ✅ Create pipeline job in Jenkins with configuration above
2. ✅ Trigger first build (manual or via webhook)
3. ✅ Monitor build output for success
4. ✅ View test results and code coverage
5. ✅ Set up SonarQube integration (optional)
6. ✅ Configure automated triggers for commits

---

**Last Updated:** April 16, 2026  
**Status:** Ready for Jenkins deployment
