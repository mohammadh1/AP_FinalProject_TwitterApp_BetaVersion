package com.company;

public enum ParameterValue {

    USERNAME {
        public void doSomething() {
            //do something
        }
    },
    PASSWORD {
        public void doSomething() {
            //do something else
        }
    },
    SIGNUP {
        public void doSomething() {
            //do something totally different
        }
    },
    SENDTWEET {
        public void doSomething() {
            //do something
        }
    },
    DELETETWEET {
        public void doSomething() {
            //do something else
        }
    },
    SHOWTWEETOF {
        public void doSomething() {
            //do something totally different
        }
    },
    TIMELINE {
        public void doSomething() {
            //do something totally different
        }
    };

    public abstract void doSomething();
}
