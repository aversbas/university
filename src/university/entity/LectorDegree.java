package university.entity;

public enum LectorDegree {
    ASSISTANT("assistant"),
    ASSOCIATE_PROFESSOR("associate professor"),
    PROFESSOR("professor");

    private final String degree;

    LectorDegree(final String degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return degree;
    }
}
