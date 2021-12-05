//
//  main.swift
//  hw6
//
//  Created by Nikolay Yarlychenko on 04.12.2021.
//

import Foundation

print("Hello, World!")

let str = "(2 + 2) / 2 - 228 * 44 - (2 + 3)"


let tokenizer = Tokenizer()

tokenizer.tokenize(str)

print(tokenizer.tokens)

