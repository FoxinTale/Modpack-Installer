/*
 * Copyright 2010 Srikanth Reddy Lingala
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zip4j.crypto.PBKDF2;

/*
 * Source referred from Matthias Gartner's PKCS#5 implementation -
 * see http://rtner.de/software/PBKDF2.html
 */
public class PBKDF2Parameters {

  protected byte[] salt;
  protected int iterationCount;
  protected String hashAlgorithm;
  protected String hashCharset;
  protected byte[] derivedKey;

  public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount) {
    this(hashAlgorithm, hashCharset, salt, iterationCount, null);
  }

  public PBKDF2Parameters(String hashAlgorithm, String hashCharset, byte[] salt, int iterationCount,
                          byte[] derivedKey) {
    this.hashAlgorithm = hashAlgorithm;
    this.hashCharset = hashCharset;
    this.salt = salt;
    this.iterationCount = iterationCount;
    this.derivedKey = derivedKey;
  }

  public int getIterationCount() {
    return iterationCount;
  }

  public byte[] getSalt() {
    return salt;
  }

  public String getHashAlgorithm() {
    return hashAlgorithm;
  }

}
